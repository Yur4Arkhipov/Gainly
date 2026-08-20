package com.jacqulin.gainly.core.data.remote.auth

import com.jacqulin.gainly.core.domain.auth.TokenRefresher
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Auto-refresh access token при 401 и повтор запроса.
 *
 * Важно: refresh делается через [TokenRefresher], который ходит в AuthApiService.
 * В данном проекте AuthApiService не требует Bearer и принимает refreshToken в body,
 * поэтому рекурсии из-за отсутствия access токена быть не должно.
 */
class TokenRefreshAuthenticator(
    private val tokenStorage: TokenStorage,
    private val tokenRefresher: TokenRefresher,
) : Authenticator {

    private val lock = ReentrantLock()

    override fun authenticate(route: Route?, response: Response): Request? {
        // Избегаем бесконечного цикла: если запрос уже ретраили, больше не пытаемся.
        if (responseCount(response) >= 2) return null

        val request = response.request
        val path = request.url.encodedPath

        // Никогда не пытаемся refresh'ить на auth эндпоинтах.
        if (path.contains("/api/auth/", ignoreCase = true)) return null

        val requestAccess = request.header("Authorization")
            ?.removePrefix("Bearer")
            ?.trim()

        return lock.withLock {
            val currentTokens = runBlocking { tokenStorage.tokens.firstOrNull() }
                ?: return@withLock null

            // Если кто-то уже обновил токен (access поменялся), просто повторяем запрос.
            if (!currentTokens.accessToken.isNullOrBlank() &&
                requestAccess != null &&
                currentTokens.accessToken != requestAccess
            ) {
                return@withLock request.newBuilder()
                    .header("Authorization", "Bearer ${currentTokens.accessToken}")
                    .build()
            }

            // Делаем refresh
            val newTokens = try {
                runBlocking { tokenRefresher.refreshToken(currentTokens.refreshToken) }
            } catch (_: Throwable) {
                // refresh не удался - чистим токены, чтобы приложение ушло в Unauthorized
                runBlocking { tokenStorage.clearTokens() }
                return@withLock null
            }

            runBlocking { tokenStorage.saveTokens(newTokens) }

            request.newBuilder()
                .header("Authorization", "Bearer ${newTokens.accessToken}")
                .build()
        }
    }

    private fun responseCount(response: Response): Int {
        var r: Response? = response
        var count = 1
        while (r?.priorResponse != null) {
            count++
            r = r.priorResponse
        }
        return count
    }
}


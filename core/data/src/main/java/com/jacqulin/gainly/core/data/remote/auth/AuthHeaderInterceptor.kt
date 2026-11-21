package com.jacqulin.gainly.core.data.remote.auth

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Добавляет заголовок Authorization: Bearer <accessToken> ко всем запросам,
 * кроме эндпоинтов авторизации (login/register/refresh и т.п.).
 */
class AuthHeaderInterceptor(
    private val tokenStorage: TokenStorage,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (shouldSkipAuth(request.url.encodedPath)) {
            return chain.proceed(request)
        }

        val tokens = runBlocking { tokenStorage.tokens.firstOrNull() }
        val accessToken = tokens?.accessToken

        if (accessToken.isNullOrBlank()) {
            return chain.proceed(request)
        }

        val authorised = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(authorised)
    }

    private fun shouldSkipAuth(path: String): Boolean {
        // auth endpoints
        if (path.contains("/api/auth/", ignoreCase = true)) return true
        // health check
        if (path.contains("/health/", ignoreCase = true)) return true
        return false
    }
}


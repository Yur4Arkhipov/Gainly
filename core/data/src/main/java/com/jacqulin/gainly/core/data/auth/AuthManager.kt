package com.jacqulin.gainly.core.data.auth

import com.jacqulin.gainly.core.domain.auth.TokenRefresher
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.auth.TokenValidator
import com.jacqulin.gainly.core.util.auth.AuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.mapLatest


@Singleton
class AuthManager @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val tokenValidator: TokenValidator,
    private val tokenRefresher: TokenRefresher
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val authState: StateFlow<AuthState> = tokenStorage.tokens
        .mapLatest { tokens ->
            when {
                tokens == null -> AuthState.Unauthorized
                tokenValidator.isAccessTokenValid(tokens.accessToken) -> AuthState.Authorized
                else -> {
                    try {
                        val newTokens = tokenRefresher.refreshToken(tokens.refreshToken)
                        tokenStorage.saveToken(newTokens)
                        AuthState.Authorized
                    } catch (e: Exception) {
                        tokenStorage.clear()
                        AuthState.Unauthorized
                    }
                }
            }
        }
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState.Loading
        )
}



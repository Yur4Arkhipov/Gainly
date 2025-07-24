package com.jacqulin.gainly.core.domain.auth

import com.jacqulin.gainly.core.domain.model.AuthData

/**
 * Interface to refresh token
 */
interface TokenRefresher {
    suspend fun refreshToken(refreshToken: String): AuthData
}

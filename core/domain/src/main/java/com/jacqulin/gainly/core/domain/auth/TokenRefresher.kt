package com.jacqulin.gainly.core.domain.auth

import com.jacqulin.gainly.core.domain.model.AuthData

interface TokenRefresher {
    suspend fun refreshToken(refreshToken: String): AuthData
}

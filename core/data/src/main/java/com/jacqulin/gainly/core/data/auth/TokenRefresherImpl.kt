package com.jacqulin.gainly.core.data.auth

import com.jacqulin.gainly.core.data.remote.dto.auth.RefreshTokenDto
import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.domain.auth.TokenRefresher
import com.jacqulin.gainly.core.domain.model.AuthData
import jakarta.inject.Inject

class TokenRefresherImpl @Inject constructor(
    private val api: AuthApiService
) : TokenRefresher {

    override suspend fun refreshToken(refreshToken: String): AuthData {
        val request = RefreshTokenDto(refreshToken)
        val response = api.refresh(request = request)
        return response
    }
}

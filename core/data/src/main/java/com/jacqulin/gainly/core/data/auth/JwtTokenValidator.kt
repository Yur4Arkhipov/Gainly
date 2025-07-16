package com.jacqulin.gainly.core.data.auth

import com.auth0.android.jwt.JWT
import com.jacqulin.gainly.core.domain.auth.TokenValidator
import jakarta.inject.Inject

class JwtTokenValidator @Inject constructor() : TokenValidator {
    override suspend fun isAccessTokenValid(accessToken: String): Boolean {
        return try {
            val jwt = JWT(accessToken)
            !jwt.isExpired(10)
        } catch (e: Exception) {
            false
        }
    }
}


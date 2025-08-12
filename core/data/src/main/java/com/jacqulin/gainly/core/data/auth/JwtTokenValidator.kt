package com.jacqulin.gainly.core.data.auth

import com.auth0.android.jwt.JWT
import com.jacqulin.gainly.core.domain.auth.TokenValidator
import jakarta.inject.Inject

/**
 * It is implementation for TokenValidator
 *
 * JWT auth is currently used in app
 *
 * Try catch for correctly token check and return false if there is exception
 */
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


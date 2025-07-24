package com.jacqulin.gainly.core.domain.auth

/**
 * Interface for validate jwt access token
 */
interface TokenValidator {
    suspend fun isAccessTokenValid(accessToken: String): Boolean
}

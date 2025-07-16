package com.jacqulin.gainly.core.domain.auth

interface TokenValidator {
    suspend fun isAccessTokenValid(accessToken: String): Boolean
}

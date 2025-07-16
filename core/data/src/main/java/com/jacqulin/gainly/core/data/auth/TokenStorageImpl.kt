package com.jacqulin.gainly.core.data.auth

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.AuthData
import jakarta.inject.Inject

class TokenStorageImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : TokenStorage {

    override suspend fun saveToken(data: AuthData) {
        tokenDataStore.saveTokens(data)
    }

    override suspend fun clear() {
        tokenDataStore.clearTokens()
    }
}

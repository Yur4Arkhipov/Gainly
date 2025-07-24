package com.jacqulin.gainly.core.data.auth

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.AuthData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class TokenStorageImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
) : TokenStorage {

    override val tokens: Flow<AuthData?> = combine(
        tokenDataStore.accessTokenFlow,
        tokenDataStore.refreshTokenFlow,
        tokenDataStore.userIdFlow
    ) { access, refresh, userId ->
        if (access == null || refresh == null || userId == null) null
        else AuthData(access, refresh, userId)
    }
    override suspend fun saveToken(data: AuthData) {
        tokenDataStore.saveTokens(data)
    }

    override suspend fun clear() {
        tokenDataStore.clearTokens()
    }
}

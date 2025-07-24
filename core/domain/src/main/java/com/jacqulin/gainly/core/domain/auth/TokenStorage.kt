package com.jacqulin.gainly.core.domain.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    val tokens: Flow<AuthData?>
    suspend fun saveToken(data: AuthData)
    suspend fun clear()
}
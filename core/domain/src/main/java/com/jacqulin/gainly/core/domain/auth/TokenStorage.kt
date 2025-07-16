package com.jacqulin.gainly.core.domain.auth

import com.jacqulin.gainly.core.domain.model.AuthData

interface TokenStorage {
    suspend fun saveToken(data: AuthData)
    suspend fun clear()
}
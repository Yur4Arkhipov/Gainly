package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<AuthData, AuthError>
    suspend fun signUp(email: String, password: String): Result<AuthData, AuthError>
    suspend fun getConfirmationCode(email: String): Result<Int, AuthError>
}
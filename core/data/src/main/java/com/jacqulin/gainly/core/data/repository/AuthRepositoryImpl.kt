package com.jacqulin.gainly.core.data.repository

import com.jacqulin.gainly.core.data.remote.dto.AuthRequestDto
import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService
): AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthData {
        val request = AuthRequestDto(email, password)
        val response = api.login(apiKey = "your-super-secret-api-key", request = request)
        return response
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): AuthData {
        val request = AuthRequestDto(email, password)
        val response = api.register(apiKey = "your-super-secret-api-key", request = request)
        return response
    }
}
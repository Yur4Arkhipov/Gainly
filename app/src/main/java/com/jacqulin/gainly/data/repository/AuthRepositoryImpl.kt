package com.jacqulin.gainly.data.repository

import com.jacqulin.gainly.data.remote.service.AuthApiService
import com.jacqulin.gainly.data.remote.dto.LoginRequestDto
import com.jacqulin.gainly.data.remote.dto.LoginResponseDto
import com.jacqulin.gainly.data.remote.dto.RegisterRequestDto
import com.jacqulin.gainly.data.remote.dto.RegisterResponseDto
import com.jacqulin.gainly.domain.repository.AuthRepository
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApiService): AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): LoginResponseDto {
        val request = LoginRequestDto(email, password)
        val response = api.login(apiKey = "your-super-secret-api-key", request = request)
        return response
    }

    override suspend fun register(
        email: String,
        password: String
    ): RegisterResponseDto {
        val request = RegisterRequestDto(email, password)
        val response = api.register(apiKey = "your-super-secret-api-key", request = request)
        return response
    }
}

package com.example.gainly.data.repository

import com.example.gainly.data.remote.AuthApiService
import com.example.gainly.data.remote.dto.RegisterRequestDto
import com.example.gainly.data.remote.dto.RegisterResponseDto
import com.example.gainly.domain.model.User
import com.example.gainly.domain.repository.AuthRepository
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApiService): AuthRepository {
    override suspend fun login(email: String, password: String): User {
        /*        val request = LoginRequestDto(email, password)
                val response = api.login(request)
                return response.toDomain()*/
        TODO("Not yet implemented")
    }

    override suspend fun register(
        email: String,
        password: String
    ): RegisterResponseDto {
        val request = RegisterRequestDto(email, password)
        val response = api.register(request, "your-super-secret-api-key")
        return response
    }
}

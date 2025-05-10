package com.example.gainly.data.remote

import com.example.gainly.data.remote.dto.LoginRequestDto
import com.example.gainly.data.remote.dto.RegisterRequestDto
import com.example.gainly.data.remote.dto.RegisterResponseDto
import com.example.gainly.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): UserDto

    @POST("/api/auth/register")
    suspend fun register(
        @Body request: RegisterRequestDto,
        @Header("x-api-key") apiKey: String
    ): RegisterResponseDto
}
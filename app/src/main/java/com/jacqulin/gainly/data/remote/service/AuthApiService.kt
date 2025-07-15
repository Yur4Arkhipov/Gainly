package com.jacqulin.gainly.data.remote.service

import com.jacqulin.gainly.data.remote.dto.LoginRequestDto
import com.jacqulin.gainly.data.remote.dto.LoginResponseDto
import com.jacqulin.gainly.data.remote.dto.RegisterRequestDto
import com.jacqulin.gainly.data.remote.dto.RegisterResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/auth/login")
    suspend fun login(
        @Header("x-api-key") apiKey: String,
        @Body request: LoginRequestDto
    ): LoginResponseDto

    @POST("/api/auth/register")
    suspend fun register(
        @Header("x-api-key") apiKey: String,
        @Body request: RegisterRequestDto
    ): RegisterResponseDto

//    @POST("/api/auth/logout")

}
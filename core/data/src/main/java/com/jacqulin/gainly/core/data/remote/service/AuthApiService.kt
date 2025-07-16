package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.AuthRequestDto
import com.jacqulin.gainly.core.domain.model.AuthData
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/auth/login")
    suspend fun login(
        @Header("x-api-key") apiKey: String,
        @Body request: AuthRequestDto
    ): AuthData

    @POST("/api/auth/register")
    suspend fun register(
        @Header("x-api-key") apiKey: String,
        @Body request: AuthRequestDto
    ): AuthData

//    @POST("/api/auth/logout")

}
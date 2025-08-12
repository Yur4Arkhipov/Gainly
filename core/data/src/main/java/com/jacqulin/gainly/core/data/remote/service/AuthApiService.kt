package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.AuthRequestDto
import com.jacqulin.gainly.core.data.remote.dto.RefreshTokenDto
import com.jacqulin.gainly.core.domain.model.AuthData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("/api/auth/refresh")
    suspend fun refresh(
        @Header("x-api-key") apiKey: String,
        @Body request: RefreshTokenDto
    ): AuthData

    @GET("/api/auth/email_code/{email}")
    suspend fun getConfirmationCode(
        @Header("x-api-key") apiKey: String,
        @Path("email") email: String
    ): Response<Int>
}
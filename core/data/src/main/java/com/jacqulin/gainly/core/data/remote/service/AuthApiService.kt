package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.AuthRequestDto
import com.jacqulin.gainly.core.data.remote.dto.GoogleSignInRequestDto
import com.jacqulin.gainly.core.data.remote.dto.OtpRequestDto
import com.jacqulin.gainly.core.data.remote.dto.RefreshTokenDto
import com.jacqulin.gainly.core.domain.model.AuthData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: AuthRequestDto
    ): AuthData

    @POST("api/auth/register")
    suspend fun register(
        @Body request: AuthRequestDto
    ): AuthData

    @POST("api/auth/refresh")
    suspend fun refresh(
        @Body request: RefreshTokenDto
    ): AuthData

    @POST("api/auth/email_code/{email}")
    suspend fun sendCodeToEmail(
        @Path("email") email: String
    ): Int

    @POST("api/auth/email_code/verify")
    suspend fun verifyCode(
        @Body request: OtpRequestDto
    )

    @POST("api/auth/google")
    suspend fun loginGoogle(
        @Body request: GoogleSignInRequestDto
    ): AuthData
}
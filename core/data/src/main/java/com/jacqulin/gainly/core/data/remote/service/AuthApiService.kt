package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.auth.AuthRequestDto
import com.jacqulin.gainly.core.data.remote.dto.auth.GoogleSignInRequestDto
import com.jacqulin.gainly.core.data.remote.dto.auth.LogoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.auth.OtpRequestDto
import com.jacqulin.gainly.core.data.remote.dto.auth.RefreshTokenDto
import com.jacqulin.gainly.core.data.remote.dto.auth.TelegramAuthRequestDto
import com.jacqulin.gainly.core.domain.model.AuthData
import retrofit2.http.Body
import retrofit2.http.HTTP
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
    )

    @POST("api/auth/email_code/verify")
    suspend fun verifyCode(
        @Body request: OtpRequestDto
    )

    @POST("api/auth/google")
    suspend fun loginGoogle(
        @Body request: GoogleSignInRequestDto
    ): AuthData

    @HTTP(method = "DELETE", path = "api/auth/logout", hasBody = true)
    suspend fun logout(
        @Body request: LogoutRequestDto
    )

    @POST("api/auth/tgloginByWidget")
    suspend fun loginTelegram(
        @Body request: TelegramAuthRequestDto
    ): AuthData
}
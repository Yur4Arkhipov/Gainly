package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.AuthRequestDto
import com.jacqulin.gainly.core.data.remote.dto.GoogleSignInRequestDto
import com.jacqulin.gainly.core.data.remote.dto.LogoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.OtpRequestDto
import com.jacqulin.gainly.core.data.remote.dto.RefreshTokenDto
import com.jacqulin.gainly.core.data.remote.dto.TelegramRequestDto
import com.jacqulin.gainly.core.domain.model.AuthData
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * ### AuthApiService
 *
 * Retrofit API interface responsible for performing all user authentication and authorization requests.
 *
 * This service provides endpoints for:
 * - Standard authentication and registration (email & password)
 * - Authentication using external providers (Google, Telegram)
 * - Token refresh and logout operations
 * - Email verification using one-time passcodes (OTP)
 *
 * ---
 * #### Common behavior
 * - All requests are **suspend functions** and must be called from a coroutine.
 * - On success, methods return either [AuthData] or Unit.
 * - On failure, methods may throw [retrofit2.HttpException] or [java.io.IOException].
 *
 * ---
 * #### Base URL
 * All endpoints are relative to API base URL: `"https://gainly.site/auth/"`
 *
 * Example full path for login request: `POST https://gainly.site/auth/api/auth/login`
 */
interface AuthApiService {

    /**
     * **Login (email/password)**
     *
     * Authenticates an existing user with email and password.
     *
     * Example JSON request:
     * ```json
     * {
     *   "email": "user@example.com",
     *   "password": "string"
     * }
     * ```
     *
     * @param request Request body containing the user's email and password.
     * @return [AuthData] containing a valid access token and refresh token.
     *
     *
     *
     */
    @POST("api/auth/login")
    suspend fun login(
        @Body request: AuthRequestDto
    ): AuthData

    /**
     * **Register (email/password)**
     *
     * Registers a new user account.
     *
     * Example JSON request:
     * ```json
     * {
     *   "email": "user@example.com",
     *   "password": "string"
     * }
     * ```
     * @param request Contains email and password for the new user.
     * @return [AuthData] with issued tokens for the newly registered account.
     *
     */
    @POST("api/auth/register")
    suspend fun register(
        @Body request: AuthRequestDto
    ): AuthData

    /**
     * **Refresh token**
     *
     * Requests new tokens using a valid refresh token.
     *
     * Example JSON request:
     * ```json
     * {
     *   "refreshToken": "string"
     * }
     * ```
     *
     * @param request [RefreshTokenDto] containing the user's refresh token.
     * @return [AuthData] with updated tokens.
     *
     */
    @POST("api/auth/refresh")
    suspend fun refresh(
        @Body request: RefreshTokenDto
    ): AuthData

    /**
     * Sends a verification code to the specified email address.
     * This api endpoint send an email with a temporary code for user authentication.
     *
     * Example usage:
     * ```kotlin
     * val response = api.sendCodeToEmail("user@example.com")
     * ```
     *
     * @param email User's email address.
     * @return [Unit]
     *
     */
    @POST("api/auth/email_code/{email}")
    suspend fun sendCodeToEmail(
        @Path("email") email: String
    )

    /**
     * **Verify email code**
     *
     * Verifies a one-time code sent to the user's email address.
     * This is used to confirm the user's email during registration.
     *
     * Example JSON request:
     * ```json
     * {
     *   "email": "string",
     *   "code": 0
     * }
     * ```
     *
     * @param request [OtpRequestDto] containing the email address and the one-time verification code.
     * @return [Unit] if the request is successful (2**), then the code is valid, otherwise the code is invalid
     */
    @POST("api/auth/email_code/verify")
    suspend fun verifyCode(
        @Body request: OtpRequestDto
    )

    /**
     * **Login with Google**
     *
     * Authenticates the user via Google Sign-In and returns access tokens ([docs](https://developer.android.com/identity/sign-in/credential-manager-siwg)).
     *
     * Example JSON request:
     * ``` json
     * {
     *   "googleIdToken": "string"
     * }
     * ```
     *
     * @param request [GoogleSignInRequestDto] containing the Google ID token.
     * @return [AuthData] with access and refresh tokens for the authenticated account.
     *
     */
    @POST("api/auth/google")
    suspend fun loginGoogle(
        @Body request: GoogleSignInRequestDto
    ): AuthData

    /**
     * **Logout**
     *
     * Revokes the user's active session by invalidating the provided refresh token.
     * After logout, the token can no longer be used to obtain new access tokens.
     *
     * Example JSON request:
     * ```json
     * {
     *   "refreshToken": "string"
     * }
     * ```
     *
     * @param request [LogoutRequestDto] containing the user's refresh token to be revoked.
     * @return [Unit]
     *
     */
    @HTTP(method = "DELETE", path = "api/auth/logout", hasBody = true)
    suspend fun logout(
        @Body request: LogoutRequestDto
    )

    /**
     * **Login with Telegram**
     *
     * Authenticates the user via Telegram verification ([docs](http://core.telegram.org/widgets/login)).
     * The client must provide Telegram user data and a valid hash received from the Telegram login widget.
     *
     * Example JSON request:
     * ```json
     * {
     *   "id": 0,
     *   "first_name": "string",
     *   "last_name": "string",
     *   "username": "string",
     *   "photo_url": "string",
     *   "auth_date": "string",
     *   "hash": "string"
     * }
     * ```
     *
     * @param request [TelegramRequestDto] containing Telegram authentication parameters via [String].
     * @return [AuthData] with access and refresh tokens for the authenticated Telegram user.
     *
     */
    @POST("/api/auth/tglogin")
    suspend fun loginTelegram(
        @Body request: TelegramRequestDto
    ): AuthData
}
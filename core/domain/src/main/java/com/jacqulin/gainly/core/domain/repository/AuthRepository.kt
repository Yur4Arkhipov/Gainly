package com.jacqulin.gainly.core.domain.repository

import android.app.Activity
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<AuthData, AuthError>
    suspend fun signUp(email: String, password: String): Result<AuthData, AuthError>
    suspend fun sendCodeToEmail(email: String): Result<Unit, AuthError>
    suspend fun verifyCode(email: String, code: Int): Result<Unit, AuthError>
    suspend fun signInGoogle(googleIdToken: String): Result<AuthData, AuthError>
    suspend fun getGoogleIdToken(activity: Activity): Result<String, AuthError>
    suspend fun logout(refreshToken: String): Result<Unit, AuthError>
    suspend fun signInTelegram(data: String): Result<AuthData, AuthError>
}
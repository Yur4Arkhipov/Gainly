package com.jacqulin.gainly.core.data.repository

import com.jacqulin.gainly.core.data.remote.dto.AuthRequestDto
import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import jakarta.inject.Inject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService
): AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthData, AuthError> {
        return try {
            val request = AuthRequestDto(email, password)
            val response = api.login(apiKey = "your-super-secret-api-key", request = request)
            Result.Success(response)
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> Result.Error(AuthError.Network.UNAUTHORIZED)
                408 -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            when (e) {
                is UnknownHostException -> Result.Error(AuthError.Network.NO_INTERNET)
                is SocketTimeoutException -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (_: Exception) {
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<AuthData, AuthError> {
        return try {
            val request = AuthRequestDto(email, password)
            val response = api.register(apiKey = "your-super-secret-api-key", request = request)
            Result.Success(response)
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> Result.Error(AuthError.Network.UNAUTHORIZED)
                408 -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            when (e) {
                is UnknownHostException -> Result.Error(AuthError.Network.NO_INTERNET)
                is SocketTimeoutException -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (_: Exception) {
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun getConfirmationCode(email: String): String {
        val response = api.getConfirmationCode(apiKey = "your-super-secret-api-key", email = email)
        return when {
            response.isSuccessful -> {
                val code = response.body()
                code?.toString() ?: "Empty answer from server"
            }
            else -> {
                response.errorBody()?.string() ?: "Unknown error"
            }
        }
    }
}
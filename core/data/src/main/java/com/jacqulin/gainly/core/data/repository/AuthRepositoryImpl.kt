package com.jacqulin.gainly.core.data.repository

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jacqulin.gainly.core.data.remote.dto.AuthRequestDto
import com.jacqulin.gainly.core.data.remote.dto.GoogleSignInRequestDto
import com.jacqulin.gainly.core.data.remote.dto.LogoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.OtpRequestDto
import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import jakarta.inject.Inject
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val credentialManager: CredentialManager,
): AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthData, AuthError> {
        return try {
            val request = AuthRequestDto(email, password)
            val response = api.login(
                request = request
            )
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
        } catch (_: SerializationException) {
            Result.Error(AuthError.Network.SERIALIZATION)
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
            val response = api.register(
                request = request
            )
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
        } catch (_: SerializationException) {
            Result.Error(AuthError.Network.SERIALIZATION)
        } catch (_: Exception) {
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun sendCodeToEmail(email: String): Result<Unit, AuthError> {
        return try {
            val response = api.sendCodeToEmail(
                email = email
            )
            Result.Success(response)
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Result.Error(AuthError.Network.BAD_REQUEST)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            when (e) {
                is UnknownHostException -> Result.Error(AuthError.Network.NO_INTERNET)
                is SocketTimeoutException -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (_: SerializationException) {
            Result.Error(AuthError.Network.SERIALIZATION)
        } catch (_: Exception) {
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun verifyCode(
        email: String,
        code: Int
    ): Result<Unit, AuthError> {
        return try {
            val request = OtpRequestDto(email, code)
            val response = api.verifyCode(request = request)
            Result.Success(response)
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Result.Error(AuthError.Network.BAD_REQUEST)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            when (e) {
                is UnknownHostException -> Result.Error(AuthError.Network.NO_INTERNET)
                is SocketTimeoutException -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (_: SerializationException) {
            Result.Error(AuthError.Network.SERIALIZATION)
        } catch (_: Exception) {
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun signInGoogle(googleIdToken: String): Result<AuthData, AuthError> {
        return try {
            val request = GoogleSignInRequestDto(googleIdToken)
            val response = api.loginGoogle(
                request = request
            )
            Result.Success(response)
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Result.Error(AuthError.Network.BAD_REQUEST)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            when (e) {
                is UnknownHostException -> Result.Error(AuthError.Network.NO_INTERNET)
                is SocketTimeoutException -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (_: SerializationException) {
            Result.Error(AuthError.Network.SERIALIZATION)
        } catch (_: Exception) {
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun getGoogleIdToken(activity: Activity): Result<String, AuthError> {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId("633406420004-df1mg5vj1nt590r3mf2ha13go3u1937b.apps.googleusercontent.com")
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(activity, request)
            val credential = result.credential
            Log.d("GOOGLE_CRED", "credential type: ${credential.type}")
            val googleIdToken = if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                GoogleIdTokenCredential.createFrom(credential.data).idToken
            } else null
            googleIdToken?.let { Result.Success(it) }
                ?: Result.Error(AuthError.GoogleToken.NO_TOKEN)
        } catch (e: Exception) {
            Log.e("GOOGLE_CRED", "Unexpected error", e)
            Result.Error(AuthError.UnknownError)
        }
    }

    override suspend fun logout(refreshToken: String): Result<Unit, AuthError> {
        return try {
            val request = LogoutRequestDto(refreshToken)
            val response = api.logout(request = request)
            Result.Success(response)
        } catch (e: HttpException) {
                Log.e("LOGOUT", "HTTP error: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                when (e.code()) {
                    400 -> Result.Error(AuthError.Network.BAD_REQUEST)
                    401 -> Result.Error(AuthError.Network.UNAUTHORIZED)
                    404 -> Result.Error(AuthError.Network.UNKNOWN)
                    in 500..599 -> Result.Error(AuthError.Network.SERVER_ERROR)
                    else -> Result.Error(AuthError.Network.UNKNOWN)
                }
            } catch (e: IOException) {
            when (e) {
                is UnknownHostException -> Result.Error(AuthError.Network.NO_INTERNET)
                is SocketTimeoutException -> Result.Error(AuthError.Network.REQUEST_TIMEOUT)
                else -> Result.Error(AuthError.Network.UNKNOWN)
            }
        } catch (_: SerializationException) {
            Result.Error(AuthError.Network.SERIALIZATION)
        } catch (e: Exception) {
            Log.e("LOGOUT", "Unexpected error", e)
            Result.Error(AuthError.UnknownError)
        }
    }
}
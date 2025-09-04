package com.jacqulin.gainly.core.util.errors

import androidx.credentials.exceptions.GetCredentialCancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {

    fun mapAuthError(e: Throwable): AuthError {
        return when (e) {
            // ---> HTTP / Network errors <---
            is HttpException ->  {
                val type = when (e.code())  {
                    400 -> AuthError.Http.BAD_REQUEST
                    401 -> AuthError.Http.UNAUTHORIZED
                    402 -> AuthError.Http.PAYMENT_REQUIRED
                    403 -> AuthError.Http.FORBIDDEN
                    404 -> AuthError.Http.NOT_FOUND
                    405 -> AuthError.Http.METHOD_NOT_ALLOWED
                    406 -> AuthError.Http.NOT_ACCEPTABLE
                    407 -> AuthError.Http.PROXY_AUTHENTICATION_REQUIRED
                    408 -> AuthError.Http.REQUEST_TIMEOUT
                    409 -> AuthError.Http.CONFLICT
                    410 -> AuthError.Http.GONE
                    411 -> AuthError.Http.LENGTH_REQUIRED
                    412 -> AuthError.Http.PRECONDITION_FAILED
                    413 -> AuthError.Http.PAYMENT_REQUIRED
                    414 -> AuthError.Http.PAYLOAD_TOO_LARGE
                    415 -> AuthError.Http.URI_TOO_LONG
                    416 -> AuthError.Http.UNSUPPORTED_MEDIA_TYPE
                    417 -> AuthError.Http.RANGE_NOT_SATISFIABLE
                    418 -> AuthError.Http.EXPECTATION_FAILED
                    419 -> AuthError.Http.I_AM_TEAPOT
                    423 -> AuthError.Http.LOCKED
                    429 -> AuthError.Http.TOO_MANY_REQUESTS
                    in 500..511 -> AuthError.Http.SERVER_ERROR
                    else -> AuthError.Http.UNKNOWN
                }
                AuthError.HttpError(type)
            }
            is IOException -> when (e) {
                is UnknownHostException -> AuthError.LocalInternetError(AuthError.LocalInternet.NO_INTERNET)
                is SocketTimeoutException -> AuthError.LocalInternetError(AuthError.LocalInternet.LOCAL_REQUEST_TIMEOUT)
                else -> AuthError.Unknown // check this if unknown error will appear
            }
            is SerializationException -> AuthError.Serialization

            // ---> Local auth errors <---

            // ---> Google Sign-In errors <---
            is GetCredentialCancellationException -> {
                AuthError.GoogleError(AuthError.Google.GOOGLE_TOKEN_ERROR)
            }

            else -> AuthError.Unknown
        }
    }
}
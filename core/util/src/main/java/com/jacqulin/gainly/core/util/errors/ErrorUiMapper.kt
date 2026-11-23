package com.jacqulin.gainly.core.util.errors

import com.jacqulin.gainly.core.util.RootError

object ErrorUiMapper {
    fun toMessage(error: RootError, context: ErrorContext = ErrorContext.GENERAL): String = when(error) {
        is AuthError -> mapUiError(error, context)
        is WorkoutError -> mapUiError(error, context)
    }

    private fun mapUiError(error: RootError, context: ErrorContext): String = when (error) {
        is AuthError.HttpError -> when (error.type) {
            AuthError.Http.BAD_REQUEST -> when (context) {
                ErrorContext.GENERAL -> "Http 400: Bad Request"
                ErrorContext.SEND_CODE -> "Account already exist"
                ErrorContext.VERIFY_CODE -> "Invalid code"
                ErrorContext.SIGN_UP -> "Error then sign up"
            }
            AuthError.Http.UNAUTHORIZED -> "Please check your email and password"
            AuthError.Http.PAYMENT_REQUIRED -> "Http 402: Payment required"
            AuthError.Http.FORBIDDEN -> "Http 403: Forbidden"
            AuthError.Http.NOT_FOUND -> "Http 404: Not found"
            AuthError.Http.METHOD_NOT_ALLOWED -> "Http 405: Method not allowed"
            AuthError.Http.NOT_ACCEPTABLE -> "Http 406: Not acceptable"
            AuthError.Http.PROXY_AUTHENTICATION_REQUIRED -> "Http 407: Proxy authentication required"
            AuthError.Http.REQUEST_TIMEOUT -> "Http 408: Request timed out"
            AuthError.Http.CONFLICT -> "Http 409: Conflict"
            AuthError.Http.GONE -> "Http 410: Gone"
            AuthError.Http.LENGTH_REQUIRED -> "Http 411: Length required"
            AuthError.Http.PRECONDITION_FAILED -> "Http 412: Precondition failed"
            AuthError.Http.PAYLOAD_TOO_LARGE -> "Http 414: Payload too large"
            AuthError.Http.URI_TOO_LONG -> "Http 415: Uri too long"
            AuthError.Http.UNSUPPORTED_MEDIA_TYPE -> "Http 416: Unsupported media type"
            AuthError.Http.RANGE_NOT_SATISFIABLE -> "Http 417: Range not satisfiable"
            AuthError.Http.EXPECTATION_FAILED -> "Http 418: Expectation failed"
            AuthError.Http.I_AM_TEAPOT -> "Http 419: I am teapot"
            AuthError.Http.LOCKED -> "Http 423: Locked"
            AuthError.Http.TOO_MANY_REQUESTS -> "Http 429: Too many requests"
            AuthError.Http.SERVER_ERROR -> "Http 5**: Server error"
            else -> "A network error has occurred. Please try again."
        }
        is AuthError.LocalInternetError -> when (error.type) {
            AuthError.LocalInternet.NO_INTERNET -> "No internet connection"
            AuthError.LocalInternet.LOCAL_REQUEST_TIMEOUT -> "Request timed out"
        }
        is AuthError.LocalError -> when (error.type) {
            AuthError.Local.STORAGE_ERROR -> "Storage error"
            AuthError.Local.TOKEN_NOT_FOUND -> "Token not found"
            AuthError.Local.INVALID_TOKEN_FORMAT -> "Invalid token format"
        }
        AuthError.Serialization -> "Data processing error"
        is AuthError.GoogleError -> when (error.type) {
            AuthError.Google.NO_TOKEN -> "Google token not found"
            AuthError.Google.GOOGLE_TOKEN_ERROR -> "Please try sign in with Google or other method again"
            AuthError.Google.CANCELLED -> "Please try sign in with Google again"
        }
        AuthError.Unknown -> "Unknown authorization error"
        is WorkoutError.HttpError -> when (error.type) {
            WorkoutError.Http.BAD_REQUEST -> when (context) {
                ErrorContext.GENERAL -> "Http 400: Bad Request"
                ErrorContext.SEND_CODE -> "Account already exist"
                ErrorContext.VERIFY_CODE -> "Invalid code"
                ErrorContext.SIGN_UP -> "Error then sign up"
            }
            WorkoutError.Http.UNAUTHORIZED -> "Please check your email and password"
            WorkoutError.Http.PAYMENT_REQUIRED -> "Http 402: Payment required"
            WorkoutError.Http.FORBIDDEN -> "Http 403: Forbidden"
            WorkoutError.Http.NOT_FOUND -> "Http 404: Not found"
            WorkoutError.Http.METHOD_NOT_ALLOWED -> "Http 405: Method not allowed"
            WorkoutError.Http.NOT_ACCEPTABLE -> "Http 406: Not acceptable"
            WorkoutError.Http.PROXY_AUTHENTICATION_REQUIRED -> "Http 407: Proxy authentication required"
            WorkoutError.Http.REQUEST_TIMEOUT -> "Http 408: Request timed out"
            WorkoutError.Http.CONFLICT -> "Http 409: Conflict"
            WorkoutError.Http.GONE -> "Http 410: Gone"
            WorkoutError.Http.LENGTH_REQUIRED -> "Http 411: Length required"
            WorkoutError.Http.PRECONDITION_FAILED -> "Http 412: Precondition failed"
            WorkoutError.Http.PAYLOAD_TOO_LARGE -> "Http 414: Payload too large"
            WorkoutError.Http.URI_TOO_LONG -> "Http 415: Uri too long"
            WorkoutError.Http.UNSUPPORTED_MEDIA_TYPE -> "Http 416: Unsupported media type"
            WorkoutError.Http.RANGE_NOT_SATISFIABLE -> "Http 417: Range not satisfiable"
            WorkoutError.Http.EXPECTATION_FAILED -> "Http 418: Expectation failed"
            WorkoutError.Http.I_AM_TEAPOT -> "Http 419: I am teapot"
            WorkoutError.Http.LOCKED -> "Http 423: Locked"
            WorkoutError.Http.TOO_MANY_REQUESTS -> "Http 429: Too many requests"
            WorkoutError.Http.SERVER_ERROR -> "Http 5**: Server error"
            else -> "A network error has occurred. Please try again."
        }
        is WorkoutError.LocalInternetError -> when (error.type) {
            WorkoutError.LocalInternet.NO_INTERNET -> "No internet connection"
            WorkoutError.LocalInternet.LOCAL_REQUEST_TIMEOUT -> "Request timed out"
        }
        is WorkoutError.LocalError -> when (error.type) {
            WorkoutError.Local.STORAGE_ERROR -> "Storage error"
            WorkoutError.Local.TOKEN_NOT_FOUND -> "Token not found"
            WorkoutError.Local.INVALID_TOKEN_FORMAT -> "Invalid token format"
        }
        WorkoutError.Serialization -> "Data processing error"
        is WorkoutError.GoogleError -> when (error.type) {
            WorkoutError.Google.NO_TOKEN -> "Google token not found"
            WorkoutError.Google.GOOGLE_TOKEN_ERROR -> "Please try sign in with Google or other method again"
            WorkoutError.Google.CANCELLED -> "Please try sign in with Google again"
        }
        WorkoutError.Unknown -> "Unknown authorization error"
    }
}
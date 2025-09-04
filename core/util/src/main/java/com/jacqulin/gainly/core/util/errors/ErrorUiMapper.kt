package com.jacqulin.gainly.core.util.errors

import com.jacqulin.gainly.core.util.RootError

object ErrorUiMapper {
    fun toMessage(error: RootError): String = when(error) {
        is AuthError -> mapAuthError(error)
    }

    private fun mapAuthError(error: AuthError): String = when (error) {
        is AuthError.HttpError -> when (error.type) {
            AuthError.Http.BAD_REQUEST -> "Account already exist"
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
            AuthError.Google.GOOGLE_TOKEN_ERROR -> "Please try sign in with Google again"
            AuthError.Google.CANCELLED -> "Please try sign in with Google again"
        }
        AuthError.Unknown -> "Unknown authorization error"
    }
}
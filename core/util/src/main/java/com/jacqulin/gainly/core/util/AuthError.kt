package com.jacqulin.gainly.core.util

sealed interface AuthError: Error {
    enum class Network: AuthError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        UNAUTHORIZED,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
    enum class Local : AuthError {
        TOKEN_NOT_FOUND,
        INVALID_TOKEN_FORMAT,
        STORAGE_ERROR
    }
//    enum class Domain : AuthError {
//    }
    object UnknownError : AuthError
}
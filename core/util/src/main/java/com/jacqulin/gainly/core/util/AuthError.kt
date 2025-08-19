package com.jacqulin.gainly.core.util

sealed interface AuthError: Error {
    enum class Network: AuthError {
        BAD_REQUEST,       // 400
        UNAUTHORIZED,      // 401
        REQUEST_TIMEOUT,   // 408 no usage now in back
        TOO_MANY_REQUESTS, // 429 no usage now in back
        PAYLOAD_TOO_LARGE, // 413 no usage now in back
        SERVER_ERROR,      // 5**,
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN
    }
    enum class Local : AuthError {
        TOKEN_NOT_FOUND,
        INVALID_TOKEN_FORMAT,
        STORAGE_ERROR
    }
    enum class GoogleToken : AuthError {
        NO_TOKEN,
        GOOGLE_TOKEN_ERROR
    }
    object UnknownError : AuthError
}
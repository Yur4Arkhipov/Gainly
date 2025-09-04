package com.jacqulin.gainly.core.util.errors

import com.jacqulin.gainly.core.util.RootError

sealed interface AuthError : RootError {

    data class HttpError(val type: Http) : AuthError
    data class LocalInternetError(val type: LocalInternet) : AuthError
    data class LocalError(val type: Local) : AuthError
    data class GoogleError(val type: Google) : AuthError

    object Serialization : AuthError
    object Unknown : AuthError

    enum class Http {
        BAD_REQUEST,                      // 400
        UNAUTHORIZED,                     // 401
        PAYMENT_REQUIRED,                 // 402
        FORBIDDEN,                        // 403
        NOT_FOUND,                        // 404
        METHOD_NOT_ALLOWED,               // 405
        NOT_ACCEPTABLE,                   // 406
        PROXY_AUTHENTICATION_REQUIRED,    // 407
        REQUEST_TIMEOUT,                  // 408
        CONFLICT,                         // 409
        GONE,                             // 410
        LENGTH_REQUIRED,                  // 411
        PRECONDITION_FAILED,              // 412
        PAYLOAD_TOO_LARGE,                // 413
        URI_TOO_LONG,                     // 414
        UNSUPPORTED_MEDIA_TYPE,           // 415
        RANGE_NOT_SATISFIABLE,            // 416
        EXPECTATION_FAILED,               // 417
        I_AM_TEAPOT,                      // 418
        LOCKED,                           // 423
        TOO_MANY_REQUESTS,                // 429
        SERVER_ERROR,                     // 5**

        UNKNOWN
    }

    enum class LocalInternet {
        NO_INTERNET,
        LOCAL_REQUEST_TIMEOUT
    }

    enum class Local {
        TOKEN_NOT_FOUND,
        INVALID_TOKEN_FORMAT,
        STORAGE_ERROR
    }

    enum class Google {
        NO_TOKEN,
        GOOGLE_TOKEN_ERROR,
        CANCELLED
    }
}
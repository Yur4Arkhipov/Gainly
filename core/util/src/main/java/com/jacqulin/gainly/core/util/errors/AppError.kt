/*
package com.jacqulin.gainly.core.util.errors

import com.jacqulin.gainly.core.util.RootError

sealed interface AppError: RootError {

    enum class Network: AppError {
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

        NO_INTERNET,
        LOCAL_REQUEST_TIMEOUT,

        SERIALIZATION,

        UNKNOWN
    }

    object UnknownAppError : AppError
}*/

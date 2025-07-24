package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.util.Response

interface SignInUseCase {
//    suspend operator fun invoke(
//        email: String,
//        password: String
//    ): Response<AuthData>
    suspend operator fun invoke(
        email: String,
        password: String
    ): AuthData
}
package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result

interface SignInUseCase {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<AuthData, AuthError>
}
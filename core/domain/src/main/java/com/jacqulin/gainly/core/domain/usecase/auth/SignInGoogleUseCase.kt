package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result

interface SignInGoogleUseCase {
    suspend operator fun invoke(googleIdToken: String): Result<AuthData, AuthError>
}
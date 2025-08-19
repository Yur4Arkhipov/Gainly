package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.SignInGoogleUseCase
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import jakarta.inject.Inject

class SignInGoogleUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignInGoogleUseCase {

    override suspend fun invoke(googleIdToken: String): Result<AuthData, AuthError> {
        return authRepository.signInGoogle(googleIdToken)
    }
}
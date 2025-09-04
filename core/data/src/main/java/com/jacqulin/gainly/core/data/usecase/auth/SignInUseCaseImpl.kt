package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result
import jakarta.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignInUseCase {

    override suspend fun invoke(
        email: String,
        password: String
    ): Result<AuthData, AuthError> {
        return authRepository.signIn(email, password)
    }
}
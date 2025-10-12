package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.SignInTelegramUseCase
import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result
import javax.inject.Inject

class SignInTelegramUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignInTelegramUseCase {

    override suspend fun invoke(data: String): Result<AuthData, AuthError> {
        return authRepository.signInTelegram(data)
    }
}
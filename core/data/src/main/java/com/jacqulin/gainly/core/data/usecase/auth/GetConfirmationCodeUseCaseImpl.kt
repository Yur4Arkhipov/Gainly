package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.GetConfirmationCodeUseCase
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import javax.inject.Inject

class GetConfirmationCodeUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GetConfirmationCodeUseCase {

    override suspend fun invoke(email: String): Result<Int, AuthError> {
        return authRepository.getConfirmationCode(email)
    }
}
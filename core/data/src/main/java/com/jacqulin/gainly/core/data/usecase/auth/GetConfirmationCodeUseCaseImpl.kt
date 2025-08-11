package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.GetConfirmationCodeUseCase
import javax.inject.Inject

class GetConfirmationCodeUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GetConfirmationCodeUseCase {
    override suspend fun invoke(email: String): String {
        return authRepository.getConfirmationCode(email)
    }
}
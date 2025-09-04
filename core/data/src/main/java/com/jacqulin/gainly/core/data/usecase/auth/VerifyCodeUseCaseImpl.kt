package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.VerifyCodeUseCase
import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result
import javax.inject.Inject

class VerifyCodeUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : VerifyCodeUseCase {

    override suspend fun invoke(
        email: String,
        code: Int
    ): Result<Unit, AuthError> {
        return authRepository.verifyCode(email, code)
    }
}
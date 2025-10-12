package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.GetRefreshTokenUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.LogoutUseCase
import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase
) : LogoutUseCase {

    override suspend fun invoke(): Result<Unit, AuthError> {
        val refreshToken = getRefreshTokenUseCase()
            ?: return Result.Error(AuthError.LocalError(AuthError.Local.TOKEN_NOT_FOUND))

        return authRepository.logout(refreshToken)
    }
}
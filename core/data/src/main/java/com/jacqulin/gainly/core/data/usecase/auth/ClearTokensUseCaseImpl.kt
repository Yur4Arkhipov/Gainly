package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.usecase.auth.ClearTokensUseCase
import jakarta.inject.Inject

class ClearTokensUseCaseImpl @Inject constructor(
    private val tokenStorage: TokenStorage
) : ClearTokensUseCase {

    override suspend fun invoke() {
        tokenStorage.clearTokens()
    }
}
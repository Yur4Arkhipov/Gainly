package com.jacqulin.gainly.core.domain.usecase.auth

interface ClearTokensUseCase {
    suspend operator fun invoke()
}
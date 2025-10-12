package com.jacqulin.gainly.core.domain.usecase.auth

interface GetRefreshTokenUseCase {
    suspend operator fun invoke(): String?
}
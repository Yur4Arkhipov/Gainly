package com.jacqulin.gainly.core.domain.usecase.auth

interface GetConfirmationCodeUseCase {
    suspend operator fun invoke(email: String): String
}
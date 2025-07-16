package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData

interface SignUpUseCase {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AuthData
}
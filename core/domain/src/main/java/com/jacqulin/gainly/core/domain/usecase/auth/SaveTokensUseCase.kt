package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData

interface SaveTokensUseCase {
    suspend operator fun invoke(data: AuthData)
}
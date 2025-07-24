package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.usecase.auth.SaveTokensUseCase
import jakarta.inject.Inject

class SaveTokensUseCaseImpl @Inject constructor(
    private val tokenStorage: TokenStorage
) : SaveTokensUseCase {

    override suspend fun invoke(data: AuthData) {
        tokenStorage.saveToken(data)
    }
}

package com.jacqulin.gainly.core.data.usecase.auth

import android.util.Log
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.usecase.auth.GetRefreshTokenUseCase
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetRefreshTokenUseCaseImpl @Inject constructor(
    private val tokenStorage: TokenStorage
) : GetRefreshTokenUseCase {

    override suspend fun invoke(): String? {
//        return tokenStorage.tokens.firstOrNull()?.refreshToken
        val token = tokenStorage.tokens.firstOrNull()?.refreshToken
        Log.d("REFRESHTOKEN", "Refresh token: $token")
        return token
    }
}
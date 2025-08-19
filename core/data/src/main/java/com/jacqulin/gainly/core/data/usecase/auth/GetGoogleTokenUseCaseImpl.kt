package com.jacqulin.gainly.core.data.usecase.auth

import android.app.Activity
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.GetGoogleIdTokenUseCase
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import jakarta.inject.Inject

class GetGoogleIdTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GetGoogleIdTokenUseCase {

    override suspend fun invoke(activity: Activity): Result<String, AuthError> {
        return authRepository.getGoogleIdToken(activity)
    }
}
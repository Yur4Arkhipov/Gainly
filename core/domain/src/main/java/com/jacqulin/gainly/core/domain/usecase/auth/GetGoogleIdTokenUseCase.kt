package com.jacqulin.gainly.core.domain.usecase.auth

import android.app.Activity
import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result

interface GetGoogleIdTokenUseCase {
    suspend operator fun invoke(activity: Activity): Result<String, AuthError>
}
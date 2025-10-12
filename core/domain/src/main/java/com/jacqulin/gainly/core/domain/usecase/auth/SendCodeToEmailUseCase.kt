package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result

interface SendCodeToEmailUseCase {
    suspend operator fun invoke(email: String): Result<Unit, AuthError>
}
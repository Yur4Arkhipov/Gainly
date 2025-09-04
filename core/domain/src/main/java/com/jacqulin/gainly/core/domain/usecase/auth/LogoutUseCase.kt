package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.util.errors.AuthError
import com.jacqulin.gainly.core.util.Result

interface LogoutUseCase {
    suspend operator fun invoke(): Result<Unit, AuthError>
}
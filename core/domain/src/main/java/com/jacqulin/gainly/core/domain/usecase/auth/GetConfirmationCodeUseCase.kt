package com.jacqulin.gainly.core.domain.usecase.auth

import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result

interface GetConfirmationCodeUseCase {
    suspend operator fun invoke(email: String): Result<Int, AuthError>
}
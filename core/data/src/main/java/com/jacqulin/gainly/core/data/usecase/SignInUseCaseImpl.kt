package com.jacqulin.gainly.core.data.usecase

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.util.Response
import jakarta.inject.Inject


class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignInUseCase {
//    override suspend fun invoke(
//        email: String,
//        password: String
//    ): Response<AuthData> {
//        return authRepository.signIn(email, password)
//    }
    override suspend fun invoke(
        email: String,
        password: String
    ): AuthData {
        return authRepository.signIn(email, password)
    }
}
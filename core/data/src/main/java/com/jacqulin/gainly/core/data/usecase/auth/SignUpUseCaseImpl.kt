package com.jacqulin.gainly.core.data.usecase.auth

import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.SignUpUseCase
import jakarta.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignUpUseCase {
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
        return authRepository.signUp(email, password)
    }
}
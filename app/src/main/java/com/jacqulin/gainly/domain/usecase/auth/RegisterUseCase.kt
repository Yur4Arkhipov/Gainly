package com.jacqulin.gainly.domain.usecase.auth

import com.jacqulin.gainly.data.remote.dto.RegisterResponseDto
import com.jacqulin.gainly.domain.repository.AuthRepository
import jakarta.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): RegisterResponseDto {
        return authRepository.register(email, password)
    }
}
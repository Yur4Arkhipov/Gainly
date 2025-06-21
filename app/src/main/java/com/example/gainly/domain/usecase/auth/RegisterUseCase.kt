package com.example.gainly.domain.usecase.auth

import com.example.gainly.data.remote.dto.RegisterResponseDto
import com.example.gainly.domain.repository.AuthRepository
import jakarta.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): RegisterResponseDto {
        return authRepository.register(email, password)
    }
}
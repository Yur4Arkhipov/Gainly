package com.example.gainly.domain.usecase.auth

import com.example.gainly.data.remote.dto.LoginResponseDto
import com.example.gainly.domain.repository.AuthRepository
import jakarta.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResponseDto {
        return authRepository.login(email, password)
    }
}
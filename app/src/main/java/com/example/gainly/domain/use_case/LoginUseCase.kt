package com.example.gainly.domain.use_case

import com.example.gainly.domain.model.User
import com.example.gainly.domain.repository.AuthRepository
import jakarta.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): User {
        return authRepository.login(email, password)
    }
}
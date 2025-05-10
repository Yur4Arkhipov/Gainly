package com.example.gainly.domain.repository

import com.example.gainly.data.remote.dto.RegisterResponseDto
import com.example.gainly.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(email: String, password: String): RegisterResponseDto
}
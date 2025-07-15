package com.jacqulin.gainly.domain.repository

import com.jacqulin.gainly.data.remote.dto.LoginResponseDto
import com.jacqulin.gainly.data.remote.dto.RegisterResponseDto

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponseDto
    suspend fun register(email: String, password: String): RegisterResponseDto
}
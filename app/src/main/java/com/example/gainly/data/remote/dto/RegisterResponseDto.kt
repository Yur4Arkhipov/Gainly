package com.example.gainly.data.remote.dto

data class RegisterResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
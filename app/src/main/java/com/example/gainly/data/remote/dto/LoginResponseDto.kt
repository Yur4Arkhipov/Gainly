package com.example.gainly.data.remote.dto

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val id: String
)
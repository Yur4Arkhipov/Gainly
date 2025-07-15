package com.jacqulin.gainly.data.remote.dto

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
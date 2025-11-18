package com.jacqulin.gainly.core.data.remote.dto.auth

data class OtpRequestDto(
    val email: String,
    val code: Int
)
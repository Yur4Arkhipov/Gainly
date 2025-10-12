package com.jacqulin.gainly.core.data.remote.dto

data class OtpRequestDto(
    val email: String,
    val code: Int
)

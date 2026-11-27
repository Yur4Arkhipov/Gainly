package com.jacqulin.gainly.core.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequestDto(
    val email: String,
    val code: Int
)
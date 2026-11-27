package com.jacqulin.gainly.core.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequestDto(
    val refreshToken: String
)
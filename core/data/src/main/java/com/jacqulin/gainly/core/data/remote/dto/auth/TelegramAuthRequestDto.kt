package com.jacqulin.gainly.core.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TelegramAuthRequestDto(
    val id: Long,
    val firstName: String,
    val lastName: String? = null,
    val username: String? = null,
    val photoUrl: String? = null,
    val authDate: Long,
    val hash: String
)
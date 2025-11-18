package com.jacqulin.gainly.core.domain.model.auth

data class TelegramUser(
    val id: Long,
    val firstName: String,
    val lastName: String? = null,
    val username: String? = null,
    val photoUrl: String? = null,
    val authDate: Long,
    val hash: String
)
package com.jacqulin.gainly.core.data.remote.dto.friends

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val username: String,
    val registrationDate: String
)
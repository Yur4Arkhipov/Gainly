package com.jacqulin.gainly.core.data.remote.dto.friends

import kotlinx.serialization.Serializable

@Serializable
data class PendingUserDto(
    val friendshipId: String,
    val fromUserId: String,
    val fromUsername: String
)
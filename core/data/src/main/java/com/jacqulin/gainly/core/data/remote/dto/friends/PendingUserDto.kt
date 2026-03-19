package com.jacqulin.gainly.core.data.remote.dto.friends

data class PendingUserDto(
    val friendshipId: String,
    val fromUserId: String,
    val fromUsername: String
)
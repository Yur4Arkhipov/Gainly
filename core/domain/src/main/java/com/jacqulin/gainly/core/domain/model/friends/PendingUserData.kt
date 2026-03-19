package com.jacqulin.gainly.core.domain.model.friends

data class PendingUserData(
    val friendshipId: String,
    val fromUserId: String,
    val fromUsername: String
)
package com.jacqulin.gainly.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
data class FriendEntity(
    @PrimaryKey
    val userId: String,
    val username: String,
    val registrationDate: String
)
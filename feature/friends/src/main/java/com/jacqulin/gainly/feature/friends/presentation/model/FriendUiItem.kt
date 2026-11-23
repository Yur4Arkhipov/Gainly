package com.jacqulin.gainly.feature.friends.presentation.model

data class FriendUiItem(
    val username: String,
    val avatarUrl: String?,
    val registrationDate: String,
    val isPending: Boolean = false
)
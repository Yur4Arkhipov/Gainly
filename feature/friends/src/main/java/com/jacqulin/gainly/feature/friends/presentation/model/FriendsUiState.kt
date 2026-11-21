package com.jacqulin.gainly.feature.friends.presentation.model

import com.jacqulin.gainly.core.domain.model.friends.FriendData

data class FriendsUiState(
    val searchQuery: String = "",
    val friends: List<FriendData> = emptyList(),
    val isLoading: Boolean = false,
)

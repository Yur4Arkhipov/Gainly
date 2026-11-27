package com.jacqulin.gainly.feature.friends.presentation.friends

import com.jacqulin.gainly.feature.friends.presentation.model.FriendUiItem

data class FriendsUiState(
    val friends: List<FriendUiItem> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val errorMessage: String? = null
)
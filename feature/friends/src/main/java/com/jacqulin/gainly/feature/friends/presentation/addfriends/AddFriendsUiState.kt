package com.jacqulin.gainly.feature.friends.presentation.addfriends

import com.jacqulin.gainly.core.domain.model.friends.UserData

data class AddFriendsUiState(
    val searchQuery: String = "",
    val searchResults: List<UserData> = emptyList(),
    val pendingUsers: List<UserData> = emptyList(),
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val errorMessage: String? = null
)
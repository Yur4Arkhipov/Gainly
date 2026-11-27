package com.jacqulin.gainly.feature.friends.presentation.model

import com.jacqulin.gainly.core.domain.model.friends.UserData

data class AddFriendsUiState(
    val searchQuery: String = "",
    val searchResult: List<UserData> = emptyList(),
    val pendingUsers: List<UserData> = emptyList(),
    val isSearching: Boolean = false,
//    val isLoading: Boolean = false,
//    val errorMessage: String? = null
)
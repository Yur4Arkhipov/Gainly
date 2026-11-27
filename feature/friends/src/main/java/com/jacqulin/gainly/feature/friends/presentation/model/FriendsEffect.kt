package com.jacqulin.gainly.feature.friends.presentation.model

sealed interface FriendsEffect {
    data object NavigateToAddFriends : FriendsEffect

    data class ShowSnackbar(val message: String) : FriendsEffect
}

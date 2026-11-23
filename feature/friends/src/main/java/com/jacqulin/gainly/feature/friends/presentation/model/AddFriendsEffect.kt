package com.jacqulin.gainly.feature.friends.presentation.model

sealed interface AddFriendsEffect {
    data object NavigateToFriends : AddFriendsEffect

    data class ShowSnackbar(val message: String) : AddFriendsEffect
}

package com.jacqulin.gainly.feature.friends.presentation.model

sealed interface AddFriendsEvent {
    data class SearchQueryChanged(
        val query: String
    ) : AddFriendsEvent

    data class SendFriendRequest(
        val username: String
    ) : AddFriendsEvent

    data class RespondFriendRequest(
        val username: String,
        val friendshipId: String,
        val accept: Boolean
    ) : AddFriendsEvent

    data object OnBackClicked : AddFriendsEvent
}

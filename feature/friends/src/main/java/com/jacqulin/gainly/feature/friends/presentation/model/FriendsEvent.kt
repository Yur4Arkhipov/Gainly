package com.jacqulin.gainly.feature.friends.presentation.model

sealed interface FriendsEvent {
    data class SearchQueryChanged(
        val query: String
    ) : FriendsEvent

    data object AddFriendsClicked : FriendsEvent
}

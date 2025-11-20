package com.jacqulin.gainly.feature.friends.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.friends.AddFriendsScreen
import kotlinx.serialization.Serializable

@Serializable data object AddFriendsRoute

fun NavController.navigateToAddFriends(navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AddFriendsRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.addFriendsScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
//    onTopicClick: (String) -> Unit,
) {
    composable<AddFriendsRoute> { entry ->
        AddFriendsScreen(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
        )
    }
}
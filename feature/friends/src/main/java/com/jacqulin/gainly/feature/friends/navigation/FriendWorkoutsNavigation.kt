package com.jacqulin.gainly.feature.friends.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.friends.presentation.screens.FriendWorkoutsScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data class FriendWorkoutsRoute(val username: String)

fun NavController.navigateToFriendWorkouts(username: String) =
    navigate(route = FriendWorkoutsRoute(username = username))

fun NavGraphBuilder.friendWorkoutsScreen(
    onBackClick: () -> Unit
) {
    composable<FriendWorkoutsRoute> {
        FriendWorkoutsScreenRoute(
            onBackClick = onBackClick
        )
    }
}

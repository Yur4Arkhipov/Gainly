package com.jacqulin.gainly.feature.friends.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jacqulin.gainly.feature.friends.presentation.screens.FriendWorkoutsScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data class FriendWorkoutsRoute(val username: String)

fun NavController.navigateToFriendWorkouts(
    username: String,
    navOptions: NavOptions? = null
) = navigate(
    route = FriendWorkoutsRoute(username = username),
    navOptions = navOptions
)

fun NavGraphBuilder.friendWorkoutsScreen(
    onBackClick: () -> Unit
) {
    composable<FriendWorkoutsRoute> { entry ->
        val route = entry.toRoute<FriendWorkoutsRoute>()
        FriendWorkoutsScreenRoute(
            username = route.username,
            onBackClick = onBackClick
        )
    }
}
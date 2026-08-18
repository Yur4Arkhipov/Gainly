package com.jacqulin.gainly.feature.friends.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jacqulin.gainly.feature.friends.ui.FriendWorkoutsScreen
import kotlinx.serialization.Serializable

@Serializable
data class FriendWorkoutsRoute(val friendId: String, val friendName: String)

fun NavController.navigateToFriendWorkouts(
    friendId: String,
    friendName: String,
    navOptions: NavOptions? = null
) = navigate(
    route = FriendWorkoutsRoute(friendId = friendId, friendName = friendName),
    navOptions = navOptions
)

fun NavGraphBuilder.friendWorkoutsScreen(
    onBackClick: () -> Unit
) {
    composable<FriendWorkoutsRoute> { entry ->
        val route = entry.toRoute<FriendWorkoutsRoute>()
        FriendWorkoutsScreen(
            friendId = route.friendId,
            friendName = route.friendName,
            onBackClick = onBackClick
        )
    }
}
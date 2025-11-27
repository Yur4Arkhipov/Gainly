package com.jacqulin.gainly.feature.friends.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jacqulin.gainly.feature.friends.ui.FriendsScreen
import kotlinx.serialization.Serializable

@Serializable data object FriendsRoute
@Serializable data object FriendsBaseRoute

fun NavController.navigateToFriends(navOptions: NavOptions) = navigate(route = FriendsRoute, navOptions)

fun NavGraphBuilder.friendsSection(
    onAddFriendsClick: () -> Unit,
    addFriendsDestination: NavGraphBuilder.() -> Unit
) {
    navigation<FriendsBaseRoute>(startDestination = FriendsRoute) {
        composable<FriendsRoute>() {
            FriendsScreen(onAddFriendsClick = onAddFriendsClick)
        }
        addFriendsDestination()
    }
}
package com.jacqulin.gainly.feature.friends.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.friends.FriendsScreen
import kotlinx.serialization.Serializable

@Serializable
data object FriendsRoute

fun NavGraphBuilder.homeNavigation(navController : NavHostController) {
    composable<FriendsRoute> {
        FriendsScreen(navController)
    }
}
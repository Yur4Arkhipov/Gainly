package com.jacqulin.gainly.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute

fun NavGraphBuilder.profileNavigation(navController : NavHostController) {
    composable<ProfileRoute> {
        ProfileScreen(navController)
    }
}
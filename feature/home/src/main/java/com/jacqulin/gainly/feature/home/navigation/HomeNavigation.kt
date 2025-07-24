package com.jacqulin.gainly.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeNavigation(navController : NavHostController) {
    composable<HomeRoute> {
        HomeScreen(navController)
    }
}
package com.jacqulin.gainly.feature.challenges.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.challenges.ChallengesScreen
import kotlinx.serialization.Serializable

@Serializable
data object ChallengesRoute

fun NavGraphBuilder.challengesNavigation(navController : NavHostController) {
    composable<ChallengesRoute> {
        ChallengesScreen(navController)
    }
}
package com.jacqulin.gainly.feature.auth.signin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.auth.signin.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
data object SignInRoute

fun NavGraphBuilder.signInNavigation(navController : NavHostController) {
    composable<SignInRoute> {
        SignInScreen(navController)
    }
}
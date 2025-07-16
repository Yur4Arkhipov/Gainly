package com.jacqulin.gainly.signin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.signin.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
data object SignInRoute

fun NavGraphBuilder.signInNavigation(navController : NavHostController) {
//    composable("signin_screen") {
//        SignInScreen()
//    }
    composable<SignInRoute> {
//        SignInScreen()
    }
}
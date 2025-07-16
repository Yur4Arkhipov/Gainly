package com.jacqulin.gainly.signup.navigaion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute

fun NavGraphBuilder.signUpNavigation(navController : NavHostController) {
//    composable("signin_screen") {
//        SignInScreen()
//    }
    composable<SignUpRoute> {
        SignUpScreen(navController)
    }
}
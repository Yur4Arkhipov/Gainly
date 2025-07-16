package com.jacqulin.gainly.feature.auth.signup.navigaion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.auth.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute

fun NavGraphBuilder.signUpNavigation(navController : NavHostController) {
    composable<SignUpRoute> {
        SignUpScreen(navController)
    }
}
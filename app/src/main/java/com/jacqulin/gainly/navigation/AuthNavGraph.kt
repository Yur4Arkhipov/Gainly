package com.jacqulin.gainly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jacqulin.gainly.feature.auth.signin.navigation.SignInRoute
import com.jacqulin.gainly.feature.auth.signin.navigation.signInNavigation
import com.jacqulin.gainly.feature.auth.signup.navigaion.signUpNavigation

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = SignInRoute
    ) {
        signInNavigation(navController = navController)
        signUpNavigation(navController = navController)
    }
}
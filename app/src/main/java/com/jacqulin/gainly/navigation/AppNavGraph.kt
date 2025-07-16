package com.jacqulin.gainly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jacqulin.gainly.signin.navigation.signInNavigation
import com.jacqulin.gainly.signup.navigaion.signUpNavigation

//@ExperimentalMaterialApi
//@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "welcome_screen"
    ) {
//        onBoardingScreen(navController = navController)
//        favoritesNavigation()
        signInNavigation(navController = navController)
        signUpNavigation(navController = navController)
//        marketScreen(navController = navController)
//        searchScreen(navController = navController)
//        detailScreen(navController = navController)
//        settingsScreen(navController = navController)
//        portfolioNavigation()
    }
}
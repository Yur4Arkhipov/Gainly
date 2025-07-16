package com.jacqulin.gainly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jacqulin.gainly.signin.navigation.SignInRoute
import com.jacqulin.gainly.signin.navigation.signInNavigation
import com.jacqulin.gainly.signup.navigaion.signUpNavigation

//@ExperimentalMaterialApi
//@ExperimentalPagerApi
@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = SignInRoute
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
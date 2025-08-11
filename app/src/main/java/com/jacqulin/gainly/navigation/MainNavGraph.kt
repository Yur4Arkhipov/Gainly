package com.jacqulin.gainly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jacqulin.gainly.feature.challenges.navigation.challengesNavigation
import com.jacqulin.gainly.feature.friends.navigation.friendsNavigation
import com.jacqulin.gainly.feature.home.navigation.HomeRoute
import com.jacqulin.gainly.feature.home.navigation.homeNavigation
import com.jacqulin.gainly.feature.profile.navigation.profileNavigation

@Composable
fun MainNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeNavigation(navController = navController)
        friendsNavigation(navController = navController)
        challengesNavigation(navController = navController)
        profileNavigation(navController = navController)
    }
}
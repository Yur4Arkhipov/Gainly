package com.jacqulin.gainly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.jacqulin.gainly.feature.friends.navigation.friendsSection
import com.jacqulin.gainly.feature.home.navigation.HomeBaseRoute
import com.jacqulin.gainly.feature.home.navigation.homeSection
import com.jacqulin.gainly.mainapp.AppState

@Composable
fun MainNavHost(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeBaseRoute,
        modifier = modifier
    ) {
        homeSection {

        }

        friendsSection {

        }
    }
}
/*
package com.jacqulin.gainly.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jacqulin.gainly.presentation.auth.login.LoginScreen
import com.jacqulin.gainly.presentation.auth.register.RegisterScreen
import com.jacqulin.gainly.presentation.data.DataScreen
import com.jacqulin.gainly.presentation.home.HomeScreen
import com.jacqulin.gainly.presentation.navigation.models.TopLevelRoute
import com.jacqulin.gainly.presentation.profile.ProfileWrapperScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute("Home", TopLevelRoutes.Home, Icons.Default.Home),
        TopLevelRoute("Data", TopLevelRoutes.Data, Icons.Default.DateRange),
        TopLevelRoute("Community", TopLevelRoutes.Community, Icons.Default.Person),
    )

    Scaffold(
        bottomBar = {
                BottomNavigation(
                    navController = navController,
                    routes = topLevelRoutes
                )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelRoutes.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<TopLevelRoutes.Home> { HomeScreen(navController) }
            composable<TopLevelRoutes.Data> { DataScreen() }
            composable<TopLevelRoutes.Community> { LoginScreen(navController) }
            composable<SubLevelRoutes.Profile> { ProfileWrapperScreen(navController) }
            composable<SubLevelRoutes.Register> { RegisterScreen() }
            composable<SubLevelRoutes.Login> { LoginScreen(navController) }
        }
    }
}
*/

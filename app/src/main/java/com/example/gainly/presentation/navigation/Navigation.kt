package com.example.gainly.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gainly.presentation.auth.AuthScreen
import com.example.gainly.presentation.auth.RequireAuthScreen
import com.example.gainly.presentation.community.CommunityScreen
import com.example.gainly.presentation.data.DataScreen
import com.example.gainly.presentation.home.HomeScreen
import com.example.gainly.presentation.navigation.models.TopLevelRoute
import com.example.gainly.presentation.profile.ProfileScreen

data class User(
    val id: Int,
    val name: String,
    val surname: String
)

class AuthState(
    var isAuthenticated: Boolean = false,
    val user: User? = null,
    val onLogin: () -> Unit = {},
    val onLogout: () -> Unit = {},
)

val LocalAuthState = staticCompositionLocalOf<AuthState> {
    error("AuthState not provided")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authState = remember { AuthState() }

    val topLevelRoutes = listOf(
        TopLevelRoute("Home", TopLevelRoutes.Home, Icons.Default.Home),
        TopLevelRoute("Data", TopLevelRoutes.Data, Icons.Default.DateRange),
        TopLevelRoute("Community", TopLevelRoutes.Community, Icons.Default.Person),
    )

    CompositionLocalProvider(LocalAuthState provides authState) {
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
                composable<TopLevelRoutes.Home> {
                    HomeScreen(navController)
                }
                composable<TopLevelRoutes.Data> { DataScreen() }
//                composable<TopLevelRoutes.Community> { CommunityScreen() }
                composable<TopLevelRoutes.Community> {
                    if (authState.isAuthenticated) {
                        CommunityScreen()
                    } else {
                        RequireAuthScreen(
                            onNavigationToAuth = { navController.navigate(SubLevelRoutes.Auth) }
                        )
                    }
                }
                /*composable<SubLevelRoutes.Profile> {
                    if (authState.isAuthenticated) {
                        ProfileScreen()
                    } else {
                        RequireAuthScreen(
                            onNavigationToAuth = { navController.navigate(SubLevelRoutes.Auth) }
                        )
                    }
                }*/
                composable<SubLevelRoutes.Profile> { ProfileScreen() }
                composable<SubLevelRoutes.Auth> {
                    AuthScreen(
                        onLoginSuccess = {
                            authState.isAuthenticated = true
                            navController.navigate(TopLevelRoutes.Home) {
                                popUpTo(SubLevelRoutes.Auth) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
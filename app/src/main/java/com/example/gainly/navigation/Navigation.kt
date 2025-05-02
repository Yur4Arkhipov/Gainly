package com.example.gainly.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gainly.DataScreen
import com.example.gainly.HomeScreen
import kotlinx.serialization.Serializable

data class TopLevelRoute(
    val name: String,
    val route: TopLevelRoutes,
    val icon: ImageVector
)

@Serializable
sealed class TopLevelRoutes {
    @Serializable object Home : TopLevelRoutes()
    @Serializable object Data : TopLevelRoutes()
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    routes: List<TopLevelRoute>
) {
    NavigationBar {
        routes.forEach { route ->
            NavigationBarItem(
                icon = { Icon(route.icon, contentDescription = route.name) },
                selected = false,
                onClick = { navController.navigate(route.route) }
            )
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute("Home", TopLevelRoutes.Home, Icons.Default.Home),
        TopLevelRoute("Data", TopLevelRoutes.Data, Icons.Default.DateRange),
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
            composable<TopLevelRoutes.Home> { HomeScreen() }
            composable<TopLevelRoutes.Data> { DataScreen() }
        }
    }
}

package com.example.gainly.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.gainly.presentation.navigation.models.TopLevelRoute


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
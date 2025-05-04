package com.example.gainly.presentation.navigation.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gainly.presentation.navigation.TopLevelRoutes

data class TopLevelRoute(
    val name: String,
    val route: TopLevelRoutes,
    val icon: ImageVector
)

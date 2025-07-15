package com.jacqulin.gainly.presentation.navigation.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.jacqulin.gainly.presentation.navigation.SubLevelRoutes

data class SubLevelRoute(
    val name: String,
    val route: SubLevelRoutes,
    val icons: ImageVector
)
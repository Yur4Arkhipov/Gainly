package com.jacqulin.gainly.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelRoutes {
    @Serializable data object Home : TopLevelRoutes()
    @Serializable data object Data : TopLevelRoutes()
    @Serializable data object Community : TopLevelRoutes()
}

@Serializable
sealed class SubLevelRoutes {
    @Serializable data object Profile : SubLevelRoutes()
    @Serializable data object Register : SubLevelRoutes()
    @Serializable data object Login : SubLevelRoutes()
}
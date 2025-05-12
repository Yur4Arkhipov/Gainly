package com.example.gainly.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelRoutes {
    @Serializable object Home : TopLevelRoutes()
    @Serializable object Data : TopLevelRoutes()
    @Serializable object Community : TopLevelRoutes()
}

@Serializable
sealed class SubLevelRoutes {
    @Serializable object Profile : SubLevelRoutes()
    @Serializable object Register : SubLevelRoutes()
    @Serializable object Login : SubLevelRoutes()
}
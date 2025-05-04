package com.example.gainly.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelRoutes {
    @Serializable object Home : TopLevelRoutes()
    @Serializable object Data : TopLevelRoutes()
    @Serializable object Community : TopLevelRoutes()
}
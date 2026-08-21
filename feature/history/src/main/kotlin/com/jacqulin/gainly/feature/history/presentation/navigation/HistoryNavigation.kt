package com.jacqulin.gainly.feature.history.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jacqulin.gainly.feature.history.presentation.ui.HistoryScreen
import kotlinx.serialization.Serializable

@Serializable
data object HistoryRoute

@Serializable
data object HistoryBaseRoute

fun NavController.navigateToHistory(navOptions: NavOptions) = navigate(route = HistoryRoute, navOptions)

fun NavGraphBuilder.historySection(
    historyDestination: NavGraphBuilder.() -> Unit
) {
    navigation<HistoryBaseRoute>(startDestination = HistoryRoute) {
        composable<HistoryRoute>() {
            HistoryScreen()
        }
        historyDestination()
    }
}
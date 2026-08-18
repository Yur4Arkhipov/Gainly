package com.jacqulin.gainly.mainapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.jacqulin.gainly.feature.friends.navigation.navigateToFriends
import com.jacqulin.gainly.feature.history.navigation.navigateToHistory
import com.jacqulin.gainly.feature.home.navigation.navigateToHome
import com.jacqulin.gainly.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
//    networkMonitor: NetworkMonitor,
//    userNewsResourceRepository: UserNewsResourceRepository,
//    timeZoneMonitor: TimeZoneMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {
//    NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
//        networkMonitor,
//        userNewsResourceRepository,
//        timeZoneMonitor,
    ) {
        AppState(
            navController = navController,
            coroutineScope = coroutineScope,
//            networkMonitor = networkMonitor,
//            userNewsResourceRepository = userNewsResourceRepository,
//            timeZoneMonitor = timeZoneMonitor,
        )
    }
}

class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
//    networkMonitor: NetworkMonitor,

) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    var showAddWorkoutSheet by mutableStateOf(false)

    fun showAddWorkoutSheet() {
        showAddWorkoutSheet = true
    }

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

//    val isOffline = networkMonitor.isOnline
//        .map(Boolean::not)
//        .stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = false,
//        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.FRIENDS -> navController.navigateToFriends(topLevelNavOptions)
                TopLevelDestination.HISTORY -> navController.navigateToHistory(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToFriends(topLevelNavOptions)
            }
        }
    }
}
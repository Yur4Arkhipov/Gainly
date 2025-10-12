package com.jacqulin.gainly.mainapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.jacqulin.gainly.navigation.MainNavHost
import kotlin.reflect.KClass

@Composable
internal fun App(
    appState: AppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {

    val currentDestination = appState.currentDestination

    val layoutType = NavigationSuiteScaffoldDefaults
        .calculateFromAdaptiveInfo(windowAdaptiveInfo)

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val selected = currentDestination.isRouteInHierarchy(destination.baseRoute)
                item(
                    selected = selected,
                    onClick = { appState.navigateToTopLevelDestination(destination) },
                    icon = {
                        if (selected) {
                            Icon(
                                imageVector = destination.selectedIcon,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = destination.unselectedIcon,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text(destination.iconTextId) },
//                    modifier = Modifier
//                        .testTag("NiaNavItem")
//                        .then(if (hasUnread) Modifier.notificationDot() else Modifier),
                )
            }
        },
        layoutType = layoutType,
    ) {
        Box(
            modifier = modifier
                .semantics { testTagsAsResourceId = true }
//                .background(Color.White) // вместо containerColor
        ) {
            MainNavHost(
                appState = appState,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
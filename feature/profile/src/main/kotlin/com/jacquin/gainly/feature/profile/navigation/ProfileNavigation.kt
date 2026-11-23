package com.jacquin.gainly.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jacquin.gainly.feature.profile.presentation.screens.ProfileScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute
@Serializable
data object ProfileBaseRoute

fun NavController.navigateToProfile(navOptions: NavOptions) = navigate(route = ProfileRoute, navOptions)

fun NavGraphBuilder.profileSection(
    profileDestinations: NavGraphBuilder.() -> Unit
) {
    navigation<ProfileBaseRoute>(startDestination = ProfileRoute) {
        composable<ProfileRoute> {
            ProfileScreenRoute()
        }
        profileDestinations()
    }
}

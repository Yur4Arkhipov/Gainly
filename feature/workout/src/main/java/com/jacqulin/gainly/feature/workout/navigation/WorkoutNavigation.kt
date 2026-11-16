package com.jacqulin.gainly.feature.workout.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jacqulin.gainly.feature.workout.presentation.screens.CreateWorkoutScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object CreateWorkoutRoute
@Serializable
data object CreateWorkoutBaseRoute

fun NavController.navigateToCreateWorkout(navOptions: NavOptions? = null) = navigate(route = CreateWorkoutRoute, navOptions)

fun NavGraphBuilder.workoutSection(
    workoutDestinations: NavGraphBuilder.() -> Unit
) {
    navigation< CreateWorkoutBaseRoute>(startDestination = CreateWorkoutRoute) {
        composable<CreateWorkoutRoute>() {
            CreateWorkoutScreenRoute()
        }
        workoutDestinations()
    }
}
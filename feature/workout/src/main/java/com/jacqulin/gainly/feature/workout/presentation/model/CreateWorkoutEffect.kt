package com.jacqulin.gainly.feature.workout.presentation.model

sealed interface CreateWorkoutEffect {
    data class NavigateToPage(val page: Int) : CreateWorkoutEffect
    data object NavigateToBack : CreateWorkoutEffect
    data class ShowSnackbar(val message: String) : CreateWorkoutEffect
}

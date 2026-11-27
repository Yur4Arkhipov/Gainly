package com.jacqulin.gainly.feature.history.presentation.model

sealed interface WorkoutsUiState {

    data object Loading : WorkoutsUiState

    data class Success(
        val workouts: List<WorkoutCardUi>
    ) : WorkoutsUiState

    data class Error(
        val message: String
    ) : WorkoutsUiState
}

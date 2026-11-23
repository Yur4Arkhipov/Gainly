package com.jacqulin.gainly.feature.friends.presentation.model

import com.jacqulin.gainly.core.domain.model.workout.Workout

sealed interface WorkoutsUiState {

    data object Loading : WorkoutsUiState

    data class Success(
        val workouts: List<Workout>
    ) : WorkoutsUiState

    data class Error(
        val message: String
    ) : WorkoutsUiState
}

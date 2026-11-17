package com.jacqulin.gainly.feature.workout.presentation.model

import androidx.compose.ui.graphics.Color
import com.jacqulin.gainly.core.designsystem.theme.BlueWorkoutColor

sealed interface CreateWorkoutUiState {

    data object Loading : CreateWorkoutUiState

    data class Content(
        val title: String = "Название тренировки",
        val selectedColor: Color = BlueWorkoutColor,
        val workoutType: WorkoutType = WorkoutType.Strength,
        val date: String = "15.11.2026",
        val time: String = "18:23",
        val note: String = "Future note",
        val exercises: List<WorkoutExerciseUi> = emptyList(),
        val selectedExerciseIds: Set<String> = emptySet(),
    ) : CreateWorkoutUiState

    data class Error(
        val message: String
    ) : CreateWorkoutUiState
}

package com.jacqulin.gainly.feature.workout.presentation.model

import androidx.compose.ui.graphics.Color
import com.jacqulin.gainly.core.designsystem.theme.BlueWorkoutColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed interface CreateWorkoutUiState {

    data object Loading : CreateWorkoutUiState

    data class Content(
        val title: String = "Тренировка",
        val selectedColor: Color = BlueWorkoutColor,
        val workoutType: WorkoutType = WorkoutType.Strength,
        val date: String = "15.11.2026",
        val time: String = "18:23",
        val note: String = "Future note",
        val exercises: List<WorkoutExerciseUi> = emptyList(),
        val selectedExerciseIds: Set<String> = emptySet(),
        val invalidRepsSetIds: Set<String> = emptySet(),
        val invalidWeightSetIds: Set<String> = emptySet(),
        val invalidExerciseTitleIds: Set<String> = emptySet()
    ) : CreateWorkoutUiState {
        companion object {
            fun initial(): Content {
                val now = LocalDateTime.now()

                return Content(
                    date = now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    time = now.format(DateTimeFormatter.ofPattern("HH:mm"))
                )
            }
        }
    }

    data class Error(
        val message: String
    ) : CreateWorkoutUiState
}

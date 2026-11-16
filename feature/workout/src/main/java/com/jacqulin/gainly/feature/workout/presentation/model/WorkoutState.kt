package com.jacqulin.gainly.feature.workout.presentation.model

import androidx.compose.ui.graphics.Color

data class WorkoutState(
    val title: String = "Название тренировки",
    val selectedColor: Color = Color(0xFF505b92),
    val workoutType: WorkoutType = WorkoutType.Cardio,
    val date: String = "",
    val isColorPickerVisible: Boolean = false,
    val isTypePickerVisible: Boolean = false,
    val isExerciseDetailVisible: Boolean = false,
    val isExercisesListVisible: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val exercises: List<WorkoutExerciseUi> = emptyList(),
    val editingExerciseId: String? = null,
    val selectedExerciseIds: Set<String> = emptySet(),
    val selectedSetIndices: Set<Int> = emptySet()
)

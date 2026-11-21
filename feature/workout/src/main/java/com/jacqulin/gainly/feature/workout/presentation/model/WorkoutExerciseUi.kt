package com.jacqulin.gainly.feature.workout.presentation.model

data class WorkoutExerciseUi(
    val id: String,
    val title: String = "",
    val isBodyWeight: Boolean = false,
    val isExpanded: Boolean = false,
    val sets: List<WorkoutSetUi> = emptyList(),
    val selectedSetIds: Set<String> = emptySet()
)

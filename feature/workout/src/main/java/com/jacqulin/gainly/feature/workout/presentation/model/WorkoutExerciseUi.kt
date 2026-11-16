package com.jacqulin.gainly.feature.workout.presentation.model

data class WorkoutExerciseUi(
    val id: String,
    val name: String = "",
    val sets: List<WorkoutSetUi> = emptyList(),
    val isBodyWeight: Boolean = false
)

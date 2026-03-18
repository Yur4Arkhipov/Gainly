package com.jacqulin.gainly.core.data.remote.dto.workout

data class WorkoutRequestDto(
    val title: String,
    val exercises: List<ExerciseDto>
)

data class ExerciseDto(
    val name: String,
    val sets: List<WorkoutSetDto>
)

data class WorkoutSetDto(
    val reps: Int,
    val weight: Int,
    val isOwnWeight: Boolean
)
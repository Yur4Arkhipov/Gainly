package com.jacqulin.gainly.core.data.remote.dto.workout

data class ExerciseDto(
    val name: String,
    val sets: List<WorkoutSetDto>
)
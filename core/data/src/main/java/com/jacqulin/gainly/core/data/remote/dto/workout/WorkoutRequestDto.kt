package com.jacqulin.gainly.core.data.remote.dto.workout

data class WorkoutRequestDto(
    val title: String,
    val exercises: List<ExerciseDto>
)
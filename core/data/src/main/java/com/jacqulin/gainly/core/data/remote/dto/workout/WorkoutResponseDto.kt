package com.jacqulin.gainly.core.data.remote.dto.workout

data class WorkoutResponseDto(
    val date: String,
    val id: String,
    val exercises: List<ExerciseDto>
)
package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutSetDto(
    val reps: Int,
    val weight: Int,
    val isOwnWeight: Boolean
)

@Serializable
data class ExerciseDto(
    val name: String,
    val sets: List<WorkoutSetDto>
)

@Serializable
data class WorkoutRequestDto(
    val title: String,
    val exercises: List<ExerciseDto>
)
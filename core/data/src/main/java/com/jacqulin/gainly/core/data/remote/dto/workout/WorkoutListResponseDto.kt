package com.jacqulin.gainly.core.data.remote.dto.workout

typealias WorkoutListResponseDto = List<WorkoutItemDto>

data class WorkoutItemDto(
    val id: String,
    val userId: String,
    val title: String,
    val date: String,
    val exercises: List<Any>? = null
)


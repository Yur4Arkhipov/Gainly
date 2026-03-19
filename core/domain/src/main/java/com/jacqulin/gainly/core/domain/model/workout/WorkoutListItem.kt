package com.jacqulin.gainly.core.domain.model.workout

data class WorkoutListItem(
    val workoutId: String,
    val userId: String? = null,
    val title: String,
    val date: String,
    val exerciseCount: Int,
    val duration: Int? = null
)


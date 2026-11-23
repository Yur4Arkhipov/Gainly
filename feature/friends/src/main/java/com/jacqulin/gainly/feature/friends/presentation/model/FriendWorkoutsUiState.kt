package com.jacqulin.gainly.feature.friends.presentation.model

import com.jacqulin.gainly.core.domain.model.workout.Workout

data class FriendWorkoutsUiState(
    val username: String = "",
    val workouts: List<Workout> = emptyList(),
    val workoutsState: WorkoutsUiState = WorkoutsUiState.Loading
)

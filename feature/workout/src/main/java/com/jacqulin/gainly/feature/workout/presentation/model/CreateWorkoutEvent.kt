package com.jacqulin.gainly.feature.workout.presentation.model

sealed interface CreateWorkoutEvent {
    data class OnTitleChanged(val title: String): CreateWorkoutEvent
    data class OnDateChanged(val date: String): CreateWorkoutEvent
    data class OnTimeChanged(val time: String): CreateWorkoutEvent
    data class OnWorkoutTypeChanged(val type: WorkoutType): CreateWorkoutEvent
}

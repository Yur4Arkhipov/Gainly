package com.jacqulin.gainly.feature.workout.presentation.model

sealed interface CreateWorkoutEvent {
    // Info page
    data class OnTitleChanged(val title: String): CreateWorkoutEvent
    data class OnDateChanged(val date: String): CreateWorkoutEvent
    data class OnTimeChanged(val time: String): CreateWorkoutEvent
    data class OnWorkoutTypeChanged(val type: WorkoutType): CreateWorkoutEvent

    // Exercises page
    data object OnCreateExercise: CreateWorkoutEvent
    data class OnToggleExerciseSelection(val exerciseId: String): CreateWorkoutEvent
    data object OnClearExerciseSelection: CreateWorkoutEvent
    data object OnDeleteSelectedExercises: CreateWorkoutEvent
}

package com.jacqulin.gainly.feature.workout.presentation.model

sealed interface CreateWorkoutEvent {
    // Info page
    data class OnWorkoutTitleChanged(
        val title: String
    ): CreateWorkoutEvent

    data class OnDateChanged(
        val date: String
    ): CreateWorkoutEvent

    data class OnTimeChanged(
        val time: String
    ): CreateWorkoutEvent

    data class OnWorkoutTypeChanged(
        val type: WorkoutType
    ): CreateWorkoutEvent

    // Exercises page
    data object OnCreateExercise: CreateWorkoutEvent

    data class OnToggleExerciseSelection(
        val exerciseId: String
    ): CreateWorkoutEvent

    data object OnClearExerciseSelection: CreateWorkoutEvent

    data object OnDeleteSelectedExercises: CreateWorkoutEvent

    data class OnExerciseExpanded(
        val exerciseId: String
    ) : CreateWorkoutEvent

    data class OnExerciseTitleChanged(
        val exerciseId: String,
        val title: String
    ) : CreateWorkoutEvent

    data class OnSetAdded(
        val exerciseId: String
    ) : CreateWorkoutEvent

    data class OnDeleteSelectedSets(
        val exerciseId: String,
    ) : CreateWorkoutEvent

    data class OnBodyWeightChanged(
        val exerciseId: String,
        val isBodyWeight: Boolean
    ) : CreateWorkoutEvent

    data class OnSetWeightChanged(
        val exerciseId: String,
        val setId: String,
        val weight: String
    ) : CreateWorkoutEvent

    data class OnSetRepetitionsChanged(
        val exerciseId: String,
        val setId: String,
        val repetitions: String
    ) : CreateWorkoutEvent

    data class OnToggleExercisesSetsSelection(
        val exerciseId: String,
        val setId: String,
    ): CreateWorkoutEvent
}

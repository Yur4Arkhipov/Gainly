package com.jacqulin.gainly.core.domain.model.workout

data class WorkoutData(
    val title: String,
    val exercises: List<ExerciseData>
)

data class WorkoutById(
    val id: String,
    val date: String,
    val exercises: List<ExerciseData>
)

data class ExerciseData(
    val name: String,
    val sets: List<WorkoutSetData>
)

data class WorkoutSetData(
    val reps: Int,
    val weight: Int,
    val isOwnWeight: Boolean
)
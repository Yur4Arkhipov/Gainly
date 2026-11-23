package com.jacqulin.gainly.feature.history.presentation.model

import com.jacqulin.gainly.core.domain.model.workout.Workout

data class WorkoutCardUi(
    val id: String,
    val title: String = "",
    val date: String = "",
    val exercises: List<ExerciseInfoUi> = emptyList()
)

fun Workout.toCardUi(): WorkoutCardUi {
    return WorkoutCardUi(
        id = id,
        title = title,
        date = date,
        exercises = exercises.map { exercise ->
            ExerciseInfoUi(
                title = exercise.title.ifBlank { "Без названия" },
                sets = exercise.sets.map { set ->
                    SetInfoUi(
                        reps = set.reps,
                        weight = set.weight,
                        isOwnWeight = set.isOwnWeight
                    )
                }
            )
        }
    )
}

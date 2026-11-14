package com.jacqulin.gainly.core.domain.model.workout

/**
 * Domain-модель упражнения
 *
 * Связанные модели: [WorkoutById] -> [Exercise] -> [Set]
 *
 * @param title название упражнения
 * @param sets список повторений
 */
data class Exercise(
    val title: String,
    val sets: List<Set>
)

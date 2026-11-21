package com.jacqulin.gainly.core.domain.model.workout

/**
 * Domain-модель тренировке по id тренировки.
 *
 * Связанные модели: [WorkoutById] -> [Exercise] -> [Set]
 *
 * @param workoutId id тренировки
 * @param date дата тренировки
 * @param exercises список упражнений
 */
data class WorkoutById(
    val workoutId: String,
    val date: String,
    val exercises: List<Exercise>
)

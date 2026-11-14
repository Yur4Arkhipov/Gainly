package com.jacqulin.gainly.core.domain.model.workout

/**
 * Domain-модель повторения
 *
 * Связанные модели: [WorkoutById] -> [Exercise] -> [Set]
 *
 * @param reps количество повторений
 * @param weight вес
 * @param isOwnWeight флаг использования собственного веса
 */
data class Set(
    val reps: Int,
    val weight: Int,
    val isOwnWeight: Boolean
)

package com.jacqulin.gainly.core.domain.model.workout

/**
 * Domain-модель тренировки
 *
 * @param date дата тренировки
 * @param title название тренировки
 * @param exercises список упражнений
 */
data class Workout(
    val id: String = "",
    val date: String,
    val title: String,
    val exercises: List<Exercise>
)

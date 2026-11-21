package com.jacqulin.gainly.core.domain.model.workout

/**
 * Domain-модель для получения информации о подходах в контексте списка
 *
 * Связанные модели: [WorkoutListItem] -> [ExerciseListItem] -> [SetListItem]
 *
 * @param setId id повторения
 * @param exerciseId id упражнения
 * @param reps количество повторений
 * @param weight вес
 * @param isOwnWeight флаг использования собственного веса
 * @param title название повторения
 */
data class SetListItem(
    val setId: String,
    val exerciseId: String,
    val reps: Int,
    val weight: Int,
    val isOwnWeight: Boolean,
    val title: String
)

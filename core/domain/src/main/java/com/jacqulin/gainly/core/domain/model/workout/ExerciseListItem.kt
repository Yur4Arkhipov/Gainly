package com.jacqulin.gainly.core.domain.model.workout

/**
 * Domain-модель для получения информации об упражнения в контексте списка
 *
 * Связанные модели: [WorkoutListItem] -> [ExerciseListItem] -> [SetListItem]
 *
 * @param exerciseId id упражнения
 * @param workoutId id тренировки
 * @param workoutId id тренировки
 * @param title название упражнения
 * @param sets список повторений
 */
data class ExerciseListItem(
    val exerciseId: String,
    val workoutId: String,
    val title: String,
    val sets: SetListItem
)

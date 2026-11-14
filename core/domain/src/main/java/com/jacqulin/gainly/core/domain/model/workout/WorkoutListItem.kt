package com.jacqulin.gainly.core.domain.model.workout

/**
 * Domain-модель для получения информации о тренировках в контексте списка
 *
 * Связанные модели: [WorkoutListItem] -> [ExerciseListItem] -> [SetListItem]
 *
 * @param workoutId id тренировки
 * @param userId id пользователя
 * @param title название тренировки
 * @param date дата тренировки
 * @param exercises список упражнений
 */
data class WorkoutListItem(
    val workoutId: String,
    val userId: String,
    val title: String,
    val date: String,
    val exercises: List<ExerciseListItem>
)

package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO-модель для получения информации о тренировке в контексте списка.
 *
 * Вызывается из эндпоинтов:
 * - `/api/Workout/workoutList`
 * - `/api/Workout/workoutList/{friendsname}`
 *
 * Связанные модели: [WorkoutListItemDto] -> [ExerciseListItemDto] -> [SetListItemDto]
 *
 * @param workoutId id тренировки
 * @param userId id пользователя
 * @param title название тренировки
 * @param date дата тренировки
 * @param exercises список упражнений
 */
@Serializable
data class WorkoutListItemDto(
    @SerialName("id") val workoutId: String,
    @SerialName("userId") val userId: String,
    @SerialName("title") val title: String,
    @SerialName("date") val date: String,
    @SerialName("exercises") val exercises: List<ExerciseListItemDto>
)

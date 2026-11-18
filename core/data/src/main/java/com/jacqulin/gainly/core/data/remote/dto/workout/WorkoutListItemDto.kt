package com.jacqulin.gainly.core.data.remote.dto.workout

import com.google.gson.annotations.SerializedName
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
    @SerializedName("id") val workoutId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("exercises") val exercises: List<ExerciseListItemDto>
)

package com.jacqulin.gainly.core.data.remote.dto.workout

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * DTO-модель для получения информации о тренировке по id тренировки.
 *
 * Вызывается из эндпоинтов:
 * - `workout/api/Workout/{workoutId}`
 *
 * Связанные модели: [WorkoutByIdDto] -> [ExerciseDto] -> [SetDto]
 *
 * @param date дата тренировки
 * @param workoutId id тренировки
 * @param exercises список упражнений
 */
@Serializable
data class WorkoutByIdDto(
    @SerializedName("date") val date: String,
    @SerializedName("id") val workoutId: String,
    @SerializedName("exercises") val exercises: List<ExerciseDto>
)

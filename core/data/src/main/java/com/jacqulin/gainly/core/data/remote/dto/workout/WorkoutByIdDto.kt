package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
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
    @SerialName("date") val date: String,
    @SerialName("id") val workoutId: String,
    @SerialName("exercises") val exercises: List<ExerciseDto>
)

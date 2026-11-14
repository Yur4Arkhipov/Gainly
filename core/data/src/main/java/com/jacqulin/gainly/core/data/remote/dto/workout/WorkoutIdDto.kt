package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO-модель для получения id тренировки
 *
 * Вызывается из эндпоинтов:
 * - `workout/api/Workout/create`
 *
 * @param workoutId id тренировки
 */
@Serializable
data class WorkoutIdDto(
    @SerialName("workoutId") val workoutId: String
)

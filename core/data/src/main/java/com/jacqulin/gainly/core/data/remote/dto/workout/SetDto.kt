package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO-модель для получения информации о подходах по id тренировки.
 *
 * Связанные модели: [WorkoutByIdDto] -> [ExerciseDto] -> [SetDto]
 *
 * @param reps количество повторов
 * @param weight вес
 * @param isOwnWeight флаг использования собственного веса
 */
@Serializable
data class SetDto(
    @SerialName("reps") val reps: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("isOwnWeight") val isOwnWeight: Boolean
)

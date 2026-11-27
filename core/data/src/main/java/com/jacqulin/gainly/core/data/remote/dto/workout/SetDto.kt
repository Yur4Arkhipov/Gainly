package com.jacqulin.gainly.core.data.remote.dto.workout

import com.google.gson.annotations.SerializedName
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
    @SerializedName("reps") val reps: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("isOwnWeight") val isOwnWeight: Boolean
)

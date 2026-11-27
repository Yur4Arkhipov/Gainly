package com.jacqulin.gainly.core.data.remote.dto.workout

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * DTO-модель для получения информации об упражнениях по id тренировки.
 *
 * Связанные модели: [WorkoutByIdDto] -> [ExerciseDto] -> [SetDto]
 *
 * @param title Название упражнения.
 * @param sets Список подходов.
 */
@Serializable
data class ExerciseDto(
    @SerializedName("name") val title: String,
    @SerializedName("sets") val sets: List<SetDto>
)

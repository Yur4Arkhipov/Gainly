package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
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
    @SerialName("name") val title: String,
    @SerialName("sets") val sets: List<SetDto>
)

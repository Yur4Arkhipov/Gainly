package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO-модель для получения информации об упражнениях в контексте списка.
 *
 * Связанные модели: [WorkoutListItemDto] -> [ExerciseListItemDto] -> [SetListItemDto]
 *
 * @param  exerciseId id упражнения
 * @param  workoutId id тренировки
 * @param  title название упражения
 * @param  sets список потворений
 */
@Serializable
data class ExerciseListItemDto(
    @SerialName("id") val exerciseId: String,
    @SerialName("workoutId") val workoutId: String,
    @SerialName("name") val title: String,
    @SerialName("sets") val sets: SetListItemDto
)

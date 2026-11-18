package com.jacqulin.gainly.core.data.remote.dto.workout

import com.google.gson.annotations.SerializedName
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
    @SerializedName("id") val exerciseId: String,
    @SerializedName("workoutId") val workoutId: String,
    @SerializedName("name") val title: String,
    @SerializedName("sets") val sets: SetListItemDto
)

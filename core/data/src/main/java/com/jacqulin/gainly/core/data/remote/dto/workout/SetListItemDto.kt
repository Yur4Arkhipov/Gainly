package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO-модель для получения информации о подходах в контексте списка.
 *
 * Связанные модели: [WorkoutListItemDto] -> [ExerciseListItemDto] -> [SetListItemDto]
 *
 * @param setId id повторения
 * @param exerciseId id упражнения
 * @param reps количество повторений
 * @param weight вес
 * @param isOwnWeight флаг использования собственного веса
 * @param title название повторения
 */
@Serializable
data class SetListItemDto(
    @SerialName("id") val setId: String,
    @SerialName("workoutExerciseId") val exerciseId: String,
    @SerialName("reps") val reps: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("isOwnWeight") val isOwnWeight: Boolean,
    @SerialName("workoutExercise") val title: String
)

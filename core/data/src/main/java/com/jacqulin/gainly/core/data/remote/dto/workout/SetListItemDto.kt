package com.jacqulin.gainly.core.data.remote.dto.workout

import com.google.gson.annotations.SerializedName
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
    @SerializedName("id") val setId: String,
    @SerializedName("workoutExerciseId") val exerciseId: String,
    @SerializedName("reps") val reps: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("isOwnWeight") val isOwnWeight: Boolean,
    @SerializedName("workoutExercise") val title: String
)

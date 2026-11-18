package com.jacqulin.gainly.core.data.remote.dto.workout

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * DTO-модель запроса на создание новой тренировки.
 *
 * Используется для отправки данных на сервер при создании тренировки.
 *
 * @param date дата тренировки
 * @param title название или заголовок тренировки
 * @param exercises список упражнений данной тренировки
 */
@Serializable
data class CreateWorkoutRequestDto(
    @SerializedName("dateTime") val date: String,
    @SerializedName("title") val title: String,
    @SerializedName("exercises") val exercises: List<ExerciseDto>
)

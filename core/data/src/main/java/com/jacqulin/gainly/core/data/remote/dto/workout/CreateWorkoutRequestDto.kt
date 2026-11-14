package com.jacqulin.gainly.core.data.remote.dto.workout

import kotlinx.serialization.SerialName
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
    @SerialName("dateTime") val date: String,
    @SerialName("title") val title: String,
    @SerialName("exercises") val exercises: List<ExerciseDto>
)

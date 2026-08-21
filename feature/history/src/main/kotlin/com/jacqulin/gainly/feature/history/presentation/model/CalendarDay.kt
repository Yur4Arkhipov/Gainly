package com.jacqulin.gainly.feature.history.presentation.model

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val dayName: String,
    val dayNumber: String
)
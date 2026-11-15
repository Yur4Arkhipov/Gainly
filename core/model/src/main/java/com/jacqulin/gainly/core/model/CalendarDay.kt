package com.jacqulin.gainly.core.model

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val dayName: String,
    val dayNumber: String
)

package com.jacqulin.gainly.feature.history.presentation.model

import com.jacqulin.gainly.core.model.CalendarDay
import java.time.LocalDate

data class CalendarDayUi(
    val date: LocalDate,
    val dayName: String,
    val dayNumber: String
)

fun CalendarDay.toUi(): CalendarDayUi {
    return CalendarDayUi(
        date = date,
        dayName = dayName,
        dayNumber = dayNumber
    )
}

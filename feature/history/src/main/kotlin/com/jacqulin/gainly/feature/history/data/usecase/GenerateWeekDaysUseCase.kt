package com.jacqulin.gainly.feature.history.data.usecase

import com.jacqulin.gainly.feature.history.presentation.model.CalendarDayUi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class GenerateWeekDaysUseCase @Inject constructor() {

    operator fun invoke(): List<CalendarDayUi> {
        val today = LocalDate.now()

        val startOfWeek = today.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        )

        return (0..6).map { offset ->
            val date = startOfWeek.plusDays(offset.toLong())

            CalendarDayUi(
                date = date,
                dayName = getWeekDayName(date),
                dayNumber = date.dayOfMonth.toString()
            )
        }
    }

    private fun getWeekDayName(
        date: LocalDate
    ): String {
        return when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> "Пн"
            DayOfWeek.TUESDAY -> "Вт"
            DayOfWeek.WEDNESDAY -> "Ср"
            DayOfWeek.THURSDAY -> "Чт"
            DayOfWeek.FRIDAY -> "Пт"
            DayOfWeek.SATURDAY -> "Сб"
            DayOfWeek.SUNDAY -> "Вс"
        }
    }
}
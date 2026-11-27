package com.jacqulin.gainly.core.domain.usecase.history

import com.jacqulin.gainly.core.model.CalendarDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class GenerateWeekDaysUseCase @Inject constructor() {

    operator fun invoke(date: LocalDate): List<CalendarDay> {
        val startOfWeek = date.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        )

        return (0..6).map { offset ->
            val currentDate = startOfWeek.plusDays(offset.toLong())

            CalendarDay(
                date = currentDate,
                dayName = getWeekDayName(currentDate),
                dayNumber = currentDate.dayOfMonth.toString()
            )
        }
    }

    private fun getWeekDayName(date: LocalDate): String {
        return listOf(
            "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"
        )[date.dayOfWeek.value - 1]
    }
}

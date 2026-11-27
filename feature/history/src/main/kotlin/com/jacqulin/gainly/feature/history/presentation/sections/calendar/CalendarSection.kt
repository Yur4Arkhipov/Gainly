package com.jacqulin.gainly.feature.history.presentation.sections.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.feature.history.presentation.model.CalendarDayUi
import java.time.LocalDate

@Composable
fun CalendarSection(
    days: List<CalendarDayUi>,
    selectedDate: LocalDate,
    month: String,
    year: String,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit,
    onMonthClick: () -> Unit,
    onTodayClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        CalendarHeader(
            month = month,
            year = year,
            onMonthClick = onMonthClick,
            onTodayClick = onTodayClick
        )

        Spacer(modifier = Modifier.height(10.dp))

        CalendarDaysRow(
            days = days,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )
    }
}

@Preview
@Composable
fun CalendarSectionPreview() {
    GainlyTheme {
        val today = LocalDate.now()
        val days = listOf(
            CalendarDayUi(today.minusDays(4), "чт", "1"),
            CalendarDayUi(today.minusDays(3), "пт", "2"),
            CalendarDayUi(today.minusDays(2),"сб", "3"),
            CalendarDayUi(today.minusDays(1), "вс", "4"),
            CalendarDayUi(today, "пн", "5"),
            CalendarDayUi(today.plusDays(1), "вт", "6"),
            CalendarDayUi(today.plusDays(2), "ср", "7"),
        )

        CalendarSection(
            days = days,
            selectedDate = today,
            month = "May",
            year = "2026",
            onDateSelected = {},
            onMonthClick = {},
            onTodayClick = {}
        )
    }
}

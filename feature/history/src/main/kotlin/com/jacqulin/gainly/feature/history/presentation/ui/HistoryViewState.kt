package com.jacqulin.gainly.feature.history.presentation.ui

import com.jacqulin.gainly.feature.history.presentation.model.CalendarDayUi
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import java.time.LocalDate

data class HistoryViewState(
    val selectedDate: LocalDate,
    val calendarDays: List<CalendarDayUi> = emptyList(),
    val isLoading: Boolean = true,
    val workouts: List<WorkoutCardUi> = emptyList(),
    val errorMessage: String? = null
)

package com.jacqulin.gainly.feature.history.presentation.model

import java.time.LocalDate

data class HistoryViewState(
    val selectedDate: LocalDate,
    val calendarDays: List<CalendarDayUi> = emptyList(),
    val isLoading: Boolean = true,
    val workouts: List<WorkoutCardUi> = emptyList(),
    val errorMessage: String? = null
)
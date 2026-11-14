package com.jacqulin.gainly.feature.history.presentation.model

import java.time.LocalDate

data class HistoryUiState(
    val selectedDate: LocalDate,
    val calendarDays: List<CalendarDayUi>,
    val trainingCards: List<WorkoutCardUi>,
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)
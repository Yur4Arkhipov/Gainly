package com.jacqulin.gainly.feature.history.presentation.ui

import com.jacqulin.gainly.feature.history.presentation.model.CalendarDayUi
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import java.time.LocalDate

data class HistoryUiState(
    val selectedDate: LocalDate,
    val calendarDays: List<CalendarDayUi>,
    val trainingCards: List<WorkoutCardUi>,
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)

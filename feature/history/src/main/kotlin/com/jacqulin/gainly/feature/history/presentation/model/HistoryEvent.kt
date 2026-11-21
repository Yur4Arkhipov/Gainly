package com.jacqulin.gainly.feature.history.presentation.model

import java.time.LocalDate

sealed interface HistoryEvent {
    data class LoadWorkoutsForDate(val date: LocalDate): HistoryEvent
    data class DateSelected(val date: LocalDate) : HistoryEvent

    data object TodayClicked : HistoryEvent

    data class MonthSelected(val date: LocalDate) : HistoryEvent
}

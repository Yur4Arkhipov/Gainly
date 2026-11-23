package com.jacqulin.gainly.feature.history.presentation.model

import java.time.LocalDate

sealed interface HistoryEvent {
    data class DateSelected(
        val date: LocalDate
    ) : HistoryEvent

    data object TodayClicked : HistoryEvent

    data class RetryLoading(
        val date: LocalDate
    ) : HistoryEvent

    data class DeleteWorkout(
        val workoutId: String
    ) : HistoryEvent
}

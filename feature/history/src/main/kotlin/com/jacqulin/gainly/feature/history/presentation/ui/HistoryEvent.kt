package com.jacqulin.gainly.feature.history.presentation.ui

import java.time.LocalDate

sealed interface HistoryEvent {
    data class LoadWorkoutsForDate(val date: LocalDate): HistoryEvent
}

package com.jacqulin.gainly.feature.history.presentation.model

import java.time.LocalDate

sealed interface HistoryEvent {
    data class LoadWorkoutsForDate(val date: LocalDate): HistoryEvent
}

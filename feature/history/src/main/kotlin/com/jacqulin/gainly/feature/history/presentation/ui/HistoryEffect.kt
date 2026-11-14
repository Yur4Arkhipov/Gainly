package com.jacqulin.gainly.feature.history.presentation.ui

sealed interface HistoryEffect {
    data class ShowError(val message: String) : HistoryEffect
}

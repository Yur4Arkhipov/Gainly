package com.jacqulin.gainly.feature.history.presentation.model

sealed interface HistoryEffect {
    data class ShowError(val message: String) : HistoryEffect
}
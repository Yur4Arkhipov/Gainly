package com.jacqulin.gainly.feature.history.presentation.model

sealed interface HistoryEffect {
    data class ShowSnackbar(val message: String) : HistoryEffect
}

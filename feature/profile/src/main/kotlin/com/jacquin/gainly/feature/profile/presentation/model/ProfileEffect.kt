package com.jacquin.gainly.feature.profile.presentation.model

sealed interface ProfileEffect {
    data class ShowMessage(val message: String) : ProfileEffect
}

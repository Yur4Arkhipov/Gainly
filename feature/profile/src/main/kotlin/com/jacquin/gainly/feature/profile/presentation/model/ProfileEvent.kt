package com.jacquin.gainly.feature.profile.presentation.model

sealed interface ProfileEvent {
    data object SignOutClicked : ProfileEvent
}

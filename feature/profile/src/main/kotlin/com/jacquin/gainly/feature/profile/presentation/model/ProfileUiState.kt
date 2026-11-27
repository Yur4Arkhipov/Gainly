package com.jacquin.gainly.feature.profile.presentation.model

data class ProfileUiState(
    val username: String = "",
    val isSigningOut: Boolean = false,
    val errorMessage: String? = null,
)

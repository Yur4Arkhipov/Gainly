package com.jacqulin.gainly.core.util

sealed class SignInEvent {
    object NavigateToHome : SignInEvent()
    data class ShowToast(val message: String) : SignInEvent()
}
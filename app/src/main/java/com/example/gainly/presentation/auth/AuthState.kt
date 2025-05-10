package com.example.gainly.presentation.auth

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    data class Error(val message: String) : AuthState()
}
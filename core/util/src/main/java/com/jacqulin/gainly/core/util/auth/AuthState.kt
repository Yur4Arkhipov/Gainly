package com.jacqulin.gainly.core.util.auth

sealed class AuthState {
    object Loading : AuthState()
    object Authorized: AuthState()
    object Unauthorized: AuthState()
}
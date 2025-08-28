package com.jacqulin.gainly.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.auth.ClearTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.LogoutUseCase
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val clearTokensUseCase: ClearTokensUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val uiState: StateFlow<UiState<String>> = _uiState

    fun logout() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            when (val result = logoutUseCase()) {
                is Result.Success -> {
                    clearTokensUseCase()
                    _uiState.value = UiState.Success("Logged out successfully")
                }
                is Result.Error -> {
                    clearTokensUseCase()
                    handleAuthError(result.error)
                }
            }
        }
    }


    private fun handleAuthError(error: AuthError) {
        val message = when (error) {
            AuthError.Network.UNAUTHORIZED -> "Please check your email and password"
            AuthError.Network.REQUEST_TIMEOUT -> "Request timed out"
            AuthError.Network.NO_INTERNET -> "No network connection"
            AuthError.Network.UNKNOWN -> "Something went wrong [AuthError->Network]"
            AuthError.UnknownError -> "Unknown error [AuthError->UnknownError]"
            AuthError.GoogleToken.NO_TOKEN -> "Google returned no token"
            AuthError.GoogleToken.GOOGLE_TOKEN_ERROR -> "Google Sign In failed"
            AuthError.Local.TOKEN_NOT_FOUND -> "Local tokens not found"
            AuthError.Local.INVALID_TOKEN_FORMAT -> "Invalid token format"
            AuthError.Local.STORAGE_ERROR -> "Storage error"
            AuthError.Network.BAD_REQUEST -> "Bad request"
            AuthError.Network.TOO_MANY_REQUESTS -> "Too many requests"
            AuthError.Network.PAYLOAD_TOO_LARGE -> "Payload too large"
            AuthError.Network.SERVER_ERROR -> "Server error"
            AuthError.Network.SERIALIZATION -> "Serialization error"
        }
        Log.d("LOGOUT", "Error: $message")
        _uiState.value = UiState.Error(message)
    }
}
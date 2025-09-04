package com.jacqulin.gainly.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.auth.ClearTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.LogoutUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
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
                    val message = ErrorUiMapper.toMessage(result.error)
                    Log.d("LOGOUT", "Error: $message")
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }
}
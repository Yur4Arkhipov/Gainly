package com.jacqulin.gainly.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.data.auth.AuthManager
import com.jacqulin.gainly.core.data.onboarding.OnboardingManager
import com.jacqulin.gainly.core.util.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val onboardingManager: OnboardingManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<RootUiState>(RootUiState.Loading)
    val uiState: StateFlow<RootUiState> = _uiState

    init {
        observeState()
    }

    fun markOnboardingComplete() {
        viewModelScope.launch {
            onboardingManager.setOnboardingShown()
            observeState()
        }
    }

    private fun observeState() {
        viewModelScope.launch {
            val onboardingShown = onboardingManager.isOnboardingShown()
            if (!onboardingShown) {
                _uiState.value = RootUiState.Onboarding
            } else {
                authManager.authState.collect { authState ->
                    _uiState.value = when (authState) {
                        AuthState.Loading -> RootUiState.Loading
                        AuthState.Unauthorized -> RootUiState.Auth
                        AuthState.Authorized -> RootUiState.Main
                    }
                }
            }
        }
    }
}

sealed class RootUiState {
    object Loading : RootUiState()
    object Onboarding : RootUiState()
    object Auth : RootUiState()
    object Main : RootUiState()
}
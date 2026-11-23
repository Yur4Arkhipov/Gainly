package com.jacquin.gainly.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.usecase.auth.ClearTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.LogoutUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import com.jacquin.gainly.feature.profile.presentation.model.ProfileEffect
import com.jacquin.gainly.feature.profile.presentation.model.ProfileEvent
import com.jacquin.gainly.feature.profile.presentation.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val clearTokensUseCase: ClearTokensUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<ProfileEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.SignOutClicked -> signOut()
        }
    }

    private fun signOut() {
        if (_uiState.value.isSigningOut) return
        _uiState.update { it.copy(isSigningOut = true, errorMessage = null) }
        viewModelScope.launch {
            when (val result = logoutUseCase()) {
                is Result.Success -> {
                    clearTokensUseCase()
                    _uiState.update { it.copy(isSigningOut = false) }
                    _effects.send(ProfileEffect.ShowMessage("Signed out successfully"))
                }
                is Result.Error -> {
                    clearTokensUseCase()
                    val message = ErrorUiMapper.toMessage(result.error)
                    _uiState.update { it.copy(isSigningOut = false, errorMessage = message) }
                    _effects.send(ProfileEffect.ShowMessage(message))
                }
            }
        }
    }
}

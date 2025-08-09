package com.jacqulin.gainly.feature.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.usecase.auth.GetConfirmationCodeUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SaveTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignUpUseCase
import com.jacqulin.gainly.core.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val saveTokensUseCase: SaveTokensUseCase,
    private val getConfirmationCodeUseCase: GetConfirmationCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthData>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthData>> = _uiState

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var userEnteredCode by mutableStateOf("")
    var serverConfirmationCode by mutableStateOf<String?>(null)

    fun requestConfirmationCode() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                serverConfirmationCode = getConfirmationCodeUseCase(email)
                _uiState.value = UiState.Idle
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Failed to get confirmation code")
            }
        }
    }

    fun confirmAndSignUp() {
        if (serverConfirmationCode == null) {
            _uiState.value = UiState.Error("Please request confirmation code first")
            return
        }

        if (userEnteredCode != serverConfirmationCode) {
            _uiState.value = UiState.Error("Invalid confirmation code")
            return
        }
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                // не забыть про git stash сделанный в sandbox
                val result = signUpUseCase(email, password)
                saveTokensUseCase(result)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Registration failed")
            }
        }
    }
}
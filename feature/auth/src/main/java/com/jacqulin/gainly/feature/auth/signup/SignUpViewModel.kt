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
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
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
    var emailConfirmState by mutableStateOf(false)

    fun requestConfirmationCode() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                serverConfirmationCode = getConfirmationCodeUseCase(email)
                val pattern = Regex("^\\d{5}\$")
                if (!pattern.matches(serverConfirmationCode.toString())) {
                    _uiState.value = UiState.Error(serverConfirmationCode.toString())
                } else {
                    emailConfirmState = true
                    _uiState.value = UiState.Idle
                }
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
            when (val result = signUpUseCase(email, password)) {
                is Result.Success -> {
                    saveTokensUseCase(result.data)
                    _uiState.value = UiState.Success(result.data)
                }
                is Result.Error -> {
                    val message = when (result.error) {
                        AuthError.Network.NO_INTERNET -> "No network connection"
                        AuthError.Network.UNAUTHORIZED -> ""
                        AuthError.Network.REQUEST_TIMEOUT -> ""
                        AuthError.UnknownError -> "Unknown error"
                        else -> "Something went wrong in signUpViewModel"
                    }
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }
}
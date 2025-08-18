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
            when (val result = getConfirmationCodeUseCase(email)) {
                is Result.Success -> {
                    serverConfirmationCode = result.data.toString()
                    emailConfirmState = true
                    _uiState.value = UiState.Idle
                }
                is Result.Error -> {
                    val message = when (result.error) {
                        AuthError.Network.BAD_REQUEST -> "Account already exist"
                        AuthError.Network.REQUEST_TIMEOUT -> "Request timed out"
                        AuthError.Network.NO_INTERNET -> "No network connection"
                        AuthError.Network.UNKNOWN -> "Something went wrong [AuthError->Network]"
                        else -> "Something went wrong in signUpViewModel [request confirmation code]"
                    }
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }

    fun confirmAndSignUp() {
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
                        AuthError.Network.UNAUTHORIZED -> "Please check your email and password"
                        AuthError.Network.REQUEST_TIMEOUT -> "Request timed out"
                        AuthError.Network.NO_INTERNET -> "No network connection"
                        AuthError.Network.UNKNOWN -> "Something went wrong [AuthError->Network]"
                        AuthError.UnknownError -> "Unknown error [AuthError->UnknownError]"
                        else -> "Something went wrong in signUpViewModel"
                    }
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }
}
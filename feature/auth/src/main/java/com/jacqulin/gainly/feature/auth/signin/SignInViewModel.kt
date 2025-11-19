package com.jacqulin.gainly.feature.auth.signin

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.usecase.auth.GetGoogleIdTokenUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SaveTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInGoogleUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInTelegramUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveTokensUseCase: SaveTokensUseCase,
    private val signInGoogleUseCase: SignInGoogleUseCase,
    private val getGoogleIdTokenUseCase: GetGoogleIdTokenUseCase,
    private val signInTelegramUseCase: SignInTelegramUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthData>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthData>> = _uiState

    var login by mutableStateOf("")
    var password by mutableStateOf("")

    fun signIn(login: String, password: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            when (val result = signInUseCase(login, password)) {
                is Result.Success -> {
                    saveTokensUseCase(result.data)
                    _uiState.value = UiState.Success(result.data)
                }
                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }

    fun signInWithGoogle(activity: Activity) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            when (val googleResult = getGoogleIdTokenUseCase(activity)) {
                is Result.Success -> {
                    val idToken = googleResult.data
                    Log.d("GOOGLE_TOKEN", "Google token: $idToken")
                    when (val signInResult = signInGoogleUseCase(idToken)) {
                        is Result.Success -> {
                            saveTokensUseCase(signInResult.data)
                            _uiState.value = UiState.Success(signInResult.data)
                        }
                        is Result.Error -> {
                            val message = ErrorUiMapper.toMessage(signInResult.error)
                            _uiState.value = UiState.Error(message)
                        }
                    }
                }
                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(googleResult.error)
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }

    fun signInWithTelegram(json: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            when (val result = signInTelegramUseCase(json)) {
                is Result.Success -> {
                    saveTokensUseCase(result.data)
                    Log.d("signInWithTelegram", "Result is not success: ${result.data}")
                    _uiState.value = UiState.Success(result.data)
                }
                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
                    Log.d("signInWithTelegram", "Result is not success: $message")
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }
}
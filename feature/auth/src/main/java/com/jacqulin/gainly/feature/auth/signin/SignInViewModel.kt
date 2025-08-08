package com.jacqulin.gainly.feature.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.usecase.auth.SaveTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveTokensUseCase: SaveTokensUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthData>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthData>> = _uiState

    var login by mutableStateOf("")
    var password by mutableStateOf("")

    fun signIn(login: String, password: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = signInUseCase(login, password)
                saveTokensUseCase(result)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
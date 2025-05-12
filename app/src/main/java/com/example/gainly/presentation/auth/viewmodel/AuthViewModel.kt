package com.example.gainly.presentation.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gainly.data.remote.dto.LoginResponseDto
import com.example.gainly.data.remote.dto.RegisterResponseDto
import com.example.gainly.data.token.TokenDataStore
import com.example.gainly.domain.use_case.LoginUseCase
import com.example.gainly.domain.use_case.RegisterUseCase
import com.example.gainly.presentation.auth.AuthState
import com.example.gainly.presentation.auth.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val tokenDataStore: TokenDataStore
): ViewModel() {

    private val _registerState = MutableStateFlow<UiState<RegisterResponseDto>>(UiState.Idle)
    val registerState: StateFlow<UiState<RegisterResponseDto>> get() = _registerState

    private val _loginState = MutableStateFlow<UiState<LoginResponseDto>>(UiState.Idle)
    val loginState: StateFlow<UiState<LoginResponseDto>> get() = _loginState

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    private val _isAuthenticated = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val isAuthenticated: StateFlow<AuthState> = _isAuthenticated

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    init {
        checkIfAuthenticated()
    }

    private fun checkIfAuthenticated() {
        viewModelScope.launch {
            tokenDataStore.accessTokenFlow.collect { token ->
                _isAuthenticated.value =
                    if (!token.isNullOrEmpty()) AuthState.Authenticated
                    else AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val loginResponse = loginUseCase(email, password)
                _loginState.value = UiState.Success(loginResponse)
                tokenDataStore.saveTokens(loginResponse.accessToken, loginResponse.refreshToken)
                _isAuthenticated.value = AuthState.Authenticated
            } catch (e: Exception) {
                _loginState.value = UiState.Error("Login failed: ${e.message}")
                _authError.value = "Login failed: ${e.message}"
                _isAuthenticated.value = AuthState.Unauthenticated
            }
        }
    }

    fun register() {
        _registerState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val registerResponse = registerUseCase(email, password)
                _registerState.value = UiState.Success(registerResponse)
                _isAuthenticated.value = AuthState.Authenticated
            } catch (e: Exception) {
                _registerState.value = UiState.Error("Registration failed: ${e.message}")
                _authError.value = "Registration failed: ${e.message}"
                _isAuthenticated.value = AuthState.Unauthenticated
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenDataStore.clearTokens()
            _isAuthenticated.value = AuthState.Unauthenticated
        }
    }
}
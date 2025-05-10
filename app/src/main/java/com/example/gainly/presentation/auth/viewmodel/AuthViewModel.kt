package com.example.gainly.presentation.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gainly.data.remote.dto.RegisterResponseDto
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
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _registerState = MutableStateFlow<UiState<RegisterResponseDto>>(UiState.Idle)
    val registerState: MutableStateFlow<UiState<RegisterResponseDto>> get() = _registerState

//    private val _loginState = MutableStateFlow<UiState<LoginResponseDto>>(UiState.Idle)
//    val loginState: MutableStateFlow<UiState<LoginResponseDto>> get() = _loginState

    private val _isAuthenticated = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val isAuthenticated: StateFlow<AuthState> = _isAuthenticated

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    /*    fun login() {
            _authState.value = UiState.Loading
            viewModelScope.launch {
                try {
                    val user = loginUseCase.invoke(email, password)
                    _authState.value = UiState.Success(user)
                } catch (e: Exception) {
                    _authState.value = UiState.Error("Login failed: ${e.message}")
                }
            }
        }*/

    fun register() {
        _registerState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val registerResponse = registerUseCase(email, password)
                _registerState.value = UiState.Success(registerResponse)
                _isAuthenticated.value = AuthState.Authenticated
            } catch (e: Exception) {
                _registerState.value = UiState.Error("Registration failed: ${e.message}")
                _isAuthenticated.value = AuthState.Error("Login failed: ${e.message}")
            }
        }
    }

    fun logout() {
        _isAuthenticated.value = AuthState.Unauthenticated
        email = ""
        password = ""
    }
}
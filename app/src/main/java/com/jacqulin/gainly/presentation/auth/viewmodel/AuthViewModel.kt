/*
package com.jacqulin.gainly.presentation.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.data.remote.dto.LoginResponseDto
import com.jacqulin.gainly.data.remote.dto.RegisterResponseDto
import com.jacqulin.gainly.data.token.TokenDataStore
import com.jacqulin.gainly.domain.usecase.auth.LoginUseCase
import com.jacqulin.gainly.domain.usecase.auth.RegisterUseCase
import com.jacqulin.gainly.presentation.auth.AuthState
import com.jacqulin.gainly.presentation.auth.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

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
        viewModelScope.launch {
            tokenDataStore.clearTokens()
        }
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
                handleError(e, "login")
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
                handleError(e, "registration")
            }
        }
    }

*/
/*    fun logout() {
        viewModelScope.launch {
            tokenDataStore.clearTokens()
            _isAuthenticated.value = AuthState.Unauthenticated
        }
    }*//*


    private fun handleError(e: Exception, action: String) {
        val errorMessage = when(e) {
            is HttpException -> {
                val errorBody = e.response()?.errorBody()?.string()
                _authError.value = "Http error: $errorBody"
                if (action == "registration") "Registration failed: ${e.message}"
                else "Login failed: ${e.message}"
            }
            else -> {
                _authError.value = "Some error: ${e.message}"
                if (action == "registration") "Registration failed: ${e.message}"
                else "Login failed: ${e.message}"
            }
        }

        if (action == "registration") {
            _registerState.value = UiState.Error(errorMessage)
        } else {
            _loginState.value = UiState.Error(errorMessage)
        }

        _isAuthenticated.value = AuthState.Unauthenticated
    }
}*/

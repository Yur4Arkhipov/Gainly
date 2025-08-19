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
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
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
    private val getGoogleIdTokenUseCase: GetGoogleIdTokenUseCase
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
                is Result.Error -> handleAuthError(result.error)
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
                        is Result.Error -> handleAuthError(signInResult.error)
                    }
                }
                is Result.Error -> handleAuthError(googleResult.error)
            }
        }
    }

    private fun handleAuthError(error: AuthError) {
        val message = when (error) {
            AuthError.Network.UNAUTHORIZED -> "Please check your email and password"
            AuthError.Network.REQUEST_TIMEOUT -> "Request timed out"
            AuthError.Network.NO_INTERNET -> "No network connection"
            AuthError.Network.UNKNOWN -> "Something went wrong [AuthError->Network]"
            AuthError.UnknownError -> "Unknown error [AuthError->UnknownError]"
            AuthError.GoogleToken.NO_TOKEN -> "Google returned no token"
            AuthError.GoogleToken.GOOGLE_TOKEN_ERROR -> "Google Sign In failed"
            AuthError.Local.TOKEN_NOT_FOUND -> "Local tokens not found"
            AuthError.Local.INVALID_TOKEN_FORMAT -> "Invalid token format"
            AuthError.Local.STORAGE_ERROR -> "Storage error"
            AuthError.Network.BAD_REQUEST -> "Bad request"
            AuthError.Network.TOO_MANY_REQUESTS -> "Too many requests"
            AuthError.Network.PAYLOAD_TOO_LARGE -> "Payload too large"
            AuthError.Network.SERVER_ERROR -> "Server error"
            AuthError.Network.SERIALIZATION -> "Serialization error"
        }
        _uiState.value = UiState.Error(message)
    }
}
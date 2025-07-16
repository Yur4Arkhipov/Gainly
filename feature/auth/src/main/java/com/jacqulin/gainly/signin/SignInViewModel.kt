package com.jacqulin.gainly.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthData>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthData>> = _uiState

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun signIn(email: String, password: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = signInUseCase(email, password)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}

//@HiltViewModel
//class SignInViewModel @Inject constructor(
//    private val signInUseCase: SignInUseCase
//) : ViewModel() {
//
//    // Для состояния экрана
//    private val _state = MutableStateFlow<UiState<Response>>(UiState.Idle)
//    val state: StateFlow<UiState<AuthResult>> get() = _state
//
//    // Для одноразовых событий
//    private val _events = MutableSharedFlow<SignInEvent>()
//    val events = _events.asSharedFlow()
//
//    fun signIn(email: String, password: String) = viewModelScope.launch {
//        _state.value = UiState.Loading
//        signInUseCase(email, password).collect { response ->
//            when (response) {
//                is Response.Success -> {
//                    _state.value = UiState.Success(response.data)
//                    _events.emit(SignInEvent.NavigateToHome)
//                }
//                is Response.Error -> {
//                    _state.value = UiState.Error(response.message)
//                    _events.emit(SignInEvent.ShowToast(response.message))
//                }
//            }
//        }
//    }
//}
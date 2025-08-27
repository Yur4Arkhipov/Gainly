package com.jacqulin.gainly.feature.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacqulin.gainly.core.domain.model.AuthData
import com.jacqulin.gainly.core.domain.usecase.auth.SendCodeToEmailUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SaveTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignUpUseCase
import com.jacqulin.gainly.core.util.AuthError
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.feature.auth.signup.otp.OtpAction
import com.jacqulin.gainly.feature.auth.signup.otp.OtpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val saveTokensUseCase: SaveTokensUseCase,
    private val sendCodeToEmailUseCase: SendCodeToEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthData>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthData>> = _uiState

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var serverConfirmationCode by mutableStateOf<Int?>(null)

    var otpState by mutableStateOf(OtpState())
        private set

    fun onOtpAction(action: OtpAction) {
        when (action) {
            is OtpAction.OnEnterNumber -> {
                enterNumber(action.number, action.index)
            }
            is OtpAction.OnChangeFieldFocused -> {
                otpState = otpState.copy(focusedIndex = action.index)
            }
            OtpAction.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(otpState.focusedIndex)
                otpState = otpState.copy(
                    code = otpState.code.mapIndexed { index, number ->
                        if (index == previousIndex) {
                            null
                        } else {
                            number
                        }
                    },
                    focusedIndex = previousIndex
                )
            }
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode = otpState.code.mapIndexed { currentIndex, currentNumber ->
            if(currentIndex == index) number else currentNumber
        }
        val wasNumberRemoved = number == null

        val allFilled = newCode.none { it == null }
        val enteredCode= newCode.joinToString("").toIntOrNull()
        val isValidLocal = if (allFilled) enteredCode == serverConfirmationCode else null

        otpState = otpState.copy(
            code = newCode,
            focusedIndex = if (wasNumberRemoved || otpState.code.getOrNull(index) != null) {
                otpState.focusedIndex
            } else {
                getNextFocusedTextFieldIndex(
                    currentCode = otpState.code,
                    currentFocusedIndex = otpState.focusedIndex
                )
            },
            isValid = isValidLocal
        )

        if (otpState.isValid == true) {
            signUp()
        } else if (otpState.isValid == false) {
            _uiState.value = UiState.Error("Invalid confirmation code")
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if(currentFocusedIndex == null) {
            return null
        }

        if(currentFocusedIndex == 4) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }

    suspend fun sendCodeToEmail(): Boolean {
        _uiState.value = UiState.Loading
        return when (val result = sendCodeToEmailUseCase(email)) {
            is Result.Success -> {
                serverConfirmationCode = result.data
                _uiState.value = UiState.Idle
                true
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
                false
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        val passwordPattern = Regex("^[a-zA-Zа-яА-Я0-9]{6,}$")
        return password.matches(passwordPattern)
    }

    private fun signUp() {
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
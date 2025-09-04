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
import com.jacqulin.gainly.core.domain.usecase.auth.VerifyCodeUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.errors.ErrorUiMapper
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
    private val sendCodeToEmailUseCase: SendCodeToEmailUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthData>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthData>> = _uiState

    var email by mutableStateOf("")
    var password by mutableStateOf("")

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

        otpState = otpState.copy(
            code = newCode,
            focusedIndex = if (wasNumberRemoved || otpState.code.getOrNull(index) != null) {
                    otpState.focusedIndex
                } else {
                    getNextFocusedTextFieldIndex(
                        currentCode = otpState.code,
                        currentFocusedIndex = otpState.focusedIndex
                    )
                }
        )

        if (allFilled) {
            val enteredCode = newCode.joinToString("") { it.toString() }.toInt()
            verifyCode(enteredCode)
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
                _uiState.value = UiState.Idle
                true
            }
            is Result.Error -> {
                val message = ErrorUiMapper.toMessage(result.error)
                _uiState.value = UiState.Error(message)
                false
            }
        }
    }

    fun verifyCode(code: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            when (val result = verifyCodeUseCase(email, code)) {
                is Result.Success -> {
                    otpState = otpState.copy(isValid = true)
                    signUp()
                }
                is Result.Error -> {
                    val message = ErrorUiMapper.toMessage(result.error)
                    _uiState.value = UiState.Error(message)
                    otpState = otpState.copy(isValid = false)
                }
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
                    val message = ErrorUiMapper.toMessage(result.error)
                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }
}
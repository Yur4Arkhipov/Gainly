package com.jacqulin.gainly.feature.auth.signup.otp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jacqulin.gainly.feature.auth.signup.SignUpViewModel

@Composable
fun OtpScreenHost(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.otpState
    val focusRequesters = remember { List(state.code.size) { FocusRequester() } }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if(allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    OtpScreen(
        state = state,
        focusRequesters = focusRequesters,
        onAction = { action ->
            when(action) {
                is OtpAction.OnEnterNumber -> {
                    if(action.number != null) {
                        focusRequesters[action.index].freeFocus()
                    }
                }
                else -> Unit
            }
            viewModel.onOtpAction(action)
        },
        navController = navController
    )
}
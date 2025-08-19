package com.jacqulin.gainly.feature.auth.signup.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jacqulin.gainly.core.designsystem.theme.Red
import com.jacqulin.gainly.core.designsystem.theme.onboardingCircularIndicatorGradient
import com.jacqulin.gainly.core.designsystem.theme.type.LocalCustomAuthTypography
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.auth.components.GradientCircularLoadingIndicator
import com.jacqulin.gainly.feature.auth.signup.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    state: OtpState,
    focusRequesters: List<FocusRequester>,
    onAction: (OtpAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Check your email",
                style = LocalCustomAuthTypography.current.modalBottomSheetText
            )
            Spacer(Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                state.code.forEachIndexed { index, number ->
                    OtpInputField(
                        number = number,
                        focusRequester = focusRequesters[index],
                        onFocusChanged = { isFocused ->
                            if (isFocused) {
                                onAction(OtpAction.OnChangeFieldFocused(index))
                            }
                        },
                        onNumberChanged = { newNumber ->
                            onAction(OtpAction.OnEnterNumber(newNumber, index))
                        },
                        onKeyboardBack = {
                            onAction(OtpAction.OnKeyboardBack)
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .aspectRatio(1f)
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            when (uiState) {
                is UiState.Loading ->
                    GradientCircularLoadingIndicator(
                        strokeWidth = 4.dp,
                        progressBrush = Brush.linearGradient(MaterialTheme.colorScheme.onboardingCircularIndicatorGradient),
                        trackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    )
                is UiState.Success -> Text("Register successful!", color = Color.Green)
                is UiState.Error -> Text(
                    (uiState as UiState.Error).message,
                    color = Red
                )
                else -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OtpScreenPreview() {
    val focusRequesters = remember { List(5) { FocusRequester() } }
    val otpState = OtpState(
        code = listOf(1, 2, 3, null, null),
        isValid = null
    )
    OtpScreen(
        state = otpState,
        focusRequesters = focusRequesters,
        onAction = {},
    )
}
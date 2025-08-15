package com.jacqulin.gainly.feature.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.ModalBottomSheetContainer
import com.jacqulin.gainly.core.designsystem.theme.Red
import com.jacqulin.gainly.core.designsystem.theme.onboardingCircularIndicatorGradient
import com.jacqulin.gainly.core.designsystem.theme.type.LocalCustomAuthTypography
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.auth.components.EmailTextField
import com.jacqulin.gainly.core.util.auth.components.GradientButton
import com.jacqulin.gainly.core.util.auth.components.GradientCircularLoadingIndicator
import com.jacqulin.gainly.core.util.auth.components.PasswordTextField
import com.jacqulin.gainly.feature.auth.signin.navigation.SignInRoute
import com.jacqulin.gainly.feature.auth.signup.otp.OtpScreenHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showOtp by remember { mutableStateOf(false) }

    val focusRequesterEmailTextField = remember { FocusRequester() }
    val focusRequesterPasswordTextField = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center).offset(y = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(
                email = viewModel.email,
                onEmailChange = { viewModel.email = it },
                focusRequester = focusRequesterEmailTextField,
                onNext = { focusRequesterPasswordTextField.requestFocus() }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                password = viewModel.password,
                onPasswordChange = { viewModel.password = it },
                focusRequester = focusRequesterPasswordTextField
            )

            Spacer(modifier = Modifier.height(16.dp))

            GradientButton(
                text = "Get code",
                onClick = {
                    scope.launch {
                        val success = viewModel.requestConfirmationCode()
                        if (success) {
                            showOtp = true
                            modalBottomSheetState.show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.email.isNotBlank() && viewModel.password.isNotBlank()
            )

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

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-30).dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account?",
                style = LocalCustomAuthTypography.current.text
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Login",
                style = LocalCustomAuthTypography.current.urlText,
                modifier = Modifier.clickable {
                    navController.navigate(SignInRoute)
                }
            )
        }
    }
    if (showOtp) {
        ModalBottomSheet(
            onDismissRequest = { showOtp = false },
            sheetState = modalBottomSheetState,
            containerColor = ModalBottomSheetContainer,
        ) {
            OtpScreenHost(viewModel = viewModel)
        }
    }
}
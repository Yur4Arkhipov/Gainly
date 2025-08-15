package com.jacqulin.gainly.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GrayBackground
import com.jacqulin.gainly.core.designsystem.theme.GrayHandleSelectedText
import com.jacqulin.gainly.core.designsystem.theme.Red
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.core.designsystem.theme.onboardingCircularIndicatorGradient
import com.jacqulin.gainly.core.designsystem.theme.type.LocalCustomAuthTypography
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.auth.components.EmailTextField
import com.jacqulin.gainly.core.util.auth.components.GradientButton
import com.jacqulin.gainly.core.util.auth.components.GradientCircularLoadingIndicator
import com.jacqulin.gainly.core.util.auth.components.PasswordTextField
import com.jacqulin.gainly.feature.auth.signup.navigaion.SignUpRoute

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                email = viewModel.login,
                onEmailChange = { viewModel.login = it },
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
                text = "Login",
                onClick = {
                    viewModel.signIn(
                        login = viewModel.login,
                        password = viewModel.password
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            DividerWithText("Or")
            Spacer(modifier = Modifier.height(30.dp))
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = GrayBackground,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Sign-In",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(25.dp)
                )
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
                is UiState.Success -> Text("Login successful!", color = Color.Green)
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
                text = "Don't have an account?",
                style = LocalCustomAuthTypography.current.text
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Register",
                style = LocalCustomAuthTypography.current.urlText,
                modifier = Modifier.clickable {
                    navController.navigate(SignUpRoute)
                }
            )
        }
    }
}

@Composable
fun DividerWithText(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Modifier.weight(1f)
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = GrayBackground
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = GrayHandleSelectedText
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = GrayBackground
        )
    }
}

package com.jacqulin.gainly.feature.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.auth.LoginTextField
import com.jacqulin.gainly.core.util.auth.PasswordTextField
import com.jacqulin.gainly.feature.auth.signin.navigation.SignInRoute

@Composable
fun SignUpScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val authState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginTextField(
            login = viewModel.email,
            onLoginChange = { viewModel.email = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            password = viewModel.password,
            onPasswordChange = { viewModel.password = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!viewModel.emailConfirmState) {
            Button(
                onClick = { viewModel.requestConfirmationCode() },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.email.isNotBlank() && viewModel.password.isNotBlank()
            ) {
                Text("Get code")
            }
        } else {
            Text("Check your email for confirmation code")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.userEnteredCode,
                onValueChange = { viewModel.userEnteredCode = it },
                label = { Text("Code confirmation") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.confirmAndSignUp() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Complete registration")
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?")
            Text(
                text = "Login",
                modifier = modifier.clickable { navController.navigate(SignInRoute) }
            )
        }

        when (authState) {
            is UiState.Loading -> {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
            is UiState.Success -> Text("Registration successful!", color = Color.Green)
            is UiState.Error -> Text((authState as UiState.Error).message, color = Color.Red)
            else -> Unit
        }
    }
}
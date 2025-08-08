package com.jacqulin.gainly.feature.auth.signin

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jacqulin.gainly.core.util.UiState
import com.jacqulin.gainly.core.util.auth.LoginTextField
import com.jacqulin.gainly.core.util.auth.PasswordTextField
import com.jacqulin.gainly.feature.auth.signup.navigaion.SignUpRoute

@Composable
fun SignInScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginTextField(
            login = viewModel.login,
            onLoginChange = { viewModel.login = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            password = viewModel.password,
            onPasswordChange = { viewModel.password = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.signIn(login = viewModel.login, password = viewModel.password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Row {
            Text(text = "Нет аккаунта?")
            Text(
                text = "Зарегистрироваться",
                modifier = modifier.clickable { navController.navigate(SignUpRoute) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> Text("Login successful!", color = Color.Green)
            is UiState.Error -> Text(
                (uiState as UiState.Error).message,
                color = Color.Red
            )
            else -> Unit
        }
    }
}
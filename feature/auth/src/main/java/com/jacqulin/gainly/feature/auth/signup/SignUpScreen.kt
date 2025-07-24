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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
        TextField(
            value = viewModel.login,
            onValueChange = { viewModel.login = it },
            label = { Text("Login") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.signUp(email = viewModel.login, password = viewModel.password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Row {
            Text(text = "Есть аккаунта?")
            Text(
                text = "Войти",
                modifier = modifier.clickable { navController.navigate(SignInRoute) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (authState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> Text("Registration successful!", color = Color.Green)
            is UiState.Error -> Text((authState as UiState.Error).message, color = Color.Red)
            else -> Unit
        }
    }
}
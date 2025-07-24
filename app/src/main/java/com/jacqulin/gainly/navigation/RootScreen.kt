package com.jacqulin.gainly.navigation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.jacqulin.gainly.core.util.auth.AuthState
import androidx.compose.runtime.getValue

@Composable
fun RootScreen(viewModel: RootViewModel = hiltViewModel()) {
    val authState by viewModel.authState.collectAsState()

    when (authState) {
        AuthState.Loading -> CircularProgressIndicator()
        AuthState.Authorized -> MainNavGraph()
        AuthState.Unauthorized -> AuthNavGraph()
    }
}


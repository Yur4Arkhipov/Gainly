package com.jacqulin.gainly.navigation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue

@Composable
fun RootScreen(viewModel: RootViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        RootUiState.Loading -> CircularProgressIndicator()
        RootUiState.Onboarding -> OnboardingNavGraph(
            onFinish = { viewModel.markOnboardingComplete() }
        )
        RootUiState.Auth -> AuthNavGraph()
        RootUiState.Main -> MainNavGraph()
    }
}


package com.jacqulin.gainly.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jacqulin.gainly.core.designsystem.theme.onboardingCircularIndicatorGradient
import com.jacqulin.gainly.core.util.auth.components.GradientCircularLoadingIndicator
import com.jacqulin.gainly.mainapp.App
import com.jacqulin.gainly.mainapp.rememberAppState

@Composable
fun RootScreen(viewModel: RootViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        RootUiState.Loading ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                GradientCircularLoadingIndicator(
                    strokeWidth = 4.dp,
                    progressBrush = Brush.linearGradient(MaterialTheme.colorScheme.onboardingCircularIndicatorGradient),
                    trackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                    modifier = Modifier.size(36.dp)
                )
            }
        RootUiState.Onboarding -> OnboardingNavGraph(
            onFinish = { viewModel.markOnboardingComplete() }
        )
        RootUiState.Auth -> AuthNavGraph()
        RootUiState.Main -> {
            val appState = rememberAppState()
            App(appState = appState)
        }
    }
}


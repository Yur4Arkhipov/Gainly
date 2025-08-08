package com.jacqulin.gainly.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.onboarding.OnboardingScreen
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingRoute

fun NavGraphBuilder.onboardingNavigation(
    onFinish: () -> Unit
) {
    composable<OnboardingRoute> {
        OnboardingScreen(
            onFinish = onFinish
        )
    }
}
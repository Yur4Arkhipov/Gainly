package com.jacqulin.gainly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jacqulin.gainly.feature.onboarding.navigation.OnboardingRoute
import com.jacqulin.gainly.feature.onboarding.navigation.onboardingNavigation

@Composable
fun OnboardingNavGraph(
    onFinish: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = OnboardingRoute
    ) {
        onboardingNavigation(onFinish)
    }
}
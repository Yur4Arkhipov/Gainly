package com.jacqulin.gainly.core.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ColorScheme

val ColorScheme.onboardingButtonGradient
//    get() = if (isLight) {
//        listOf(LightOnboardingGradientFirst, LightOnboardingGradientSecond)
//    } else {
//        listOf(DarkOnboardingGradientFirst, DarkOnboardingGradientSecond)
//    }
    get() = listOf(LightOnboardingGradientFirst, LightOnboardingGradientSecond)

val ColorScheme.onboardingCircularIndicatorGradient
    get() = listOf(LightOnboardingGradientFirst, LightOnboardingGradientSecond)


fun ColorScheme.authGradientButton(alpha: Float = 1f): List<Color> =
     listOf(
         LightAuthGradientButtonFirst.copy(alpha = alpha),
         LightAuthGradientButtonSecond.copy(alpha = alpha)
     )
package com.jacqulin.gainly.core.designsystem.theme.type

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.OnboardingTextColor

data class CustomOnboardingTypography(
    val onboardingTitle: TextStyle,
    val onboardingDescription: TextStyle,
)

val LocalCustomOnboardingTypography = staticCompositionLocalOf {
    CustomOnboardingTypography(
        onboardingTitle = TextStyle(
            fontFamily = GainlyFontFamily,
            fontWeight = FontWeight.W700,
            color = OnboardingTextColor,
            fontSize = 32.sp,
        ),
        onboardingDescription = TextStyle(
            fontFamily = GainlyFontFamily,
            fontWeight = FontWeight.W400,
            color = OnboardingTextColor,
            fontSize = 16.sp,
        ),
    )
}

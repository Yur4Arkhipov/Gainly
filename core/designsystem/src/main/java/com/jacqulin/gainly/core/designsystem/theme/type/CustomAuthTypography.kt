package com.jacqulin.gainly.core.designsystem.theme.type

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayText
import com.jacqulin.gainly.core.designsystem.theme.LightAuthButtonTextColor
import com.jacqulin.gainly.core.designsystem.theme.UrlTextColor


data class CustomAuthTypography(
    val button: TextStyle,
    val text: TextStyle,
    val urlText: TextStyle,
    val helpText: TextStyle,
    val codeDigits: TextStyle
)

val LocalCustomAuthTypography = staticCompositionLocalOf {
    CustomAuthTypography(
        button = TextStyle(
            fontFamily = GainlyFontFamily,
            fontWeight = FontWeight.W700,
            color = LightAuthButtonTextColor,
            fontSize = 20.sp,
        ),
        text = TextStyle(
            fontFamily = GainlyFontFamily,
            fontWeight = FontWeight.W600,
            color = GrayText,
            fontSize = 16.sp
        ),
        urlText = TextStyle(
            fontFamily = GainlyFontFamily,
            fontWeight = FontWeight.W600,
            color = UrlTextColor,
            fontSize = 16.sp
        ),
        helpText = TextStyle(
            fontFamily = GainlyFontFamily,
            fontWeight = FontWeight.W400,
            color = GrayText,
            fontSize = 18.sp
        ),
        codeDigits = TextStyle(
            fontFamily = GainlyFontFamily,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400,
            color = GrayText,
            fontSize = 20.sp
        )
    )
}
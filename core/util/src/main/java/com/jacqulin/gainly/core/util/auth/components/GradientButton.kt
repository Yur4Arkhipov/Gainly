package com.jacqulin.gainly.core.util.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.core.designsystem.theme.authGradientButton
import com.jacqulin.gainly.core.designsystem.theme.type.LocalCustomAuthTypography

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val backgroundBrush = Brush.linearGradient(
        colors = if (enabled) {
            MaterialTheme.colorScheme.authGradientButton()
        } else {
            MaterialTheme.colorScheme.authGradientButton(alpha = 0.4f)
        }
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundBrush)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(vertical = 12.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = White.copy(alpha = if (enabled) 1f else 0.5f),
            style = LocalCustomAuthTypography.current.button
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GradientButtonPreview() {
    GainlyTheme {
        GradientButton(
            text = "Зарегистрироваться",
            onClick = {  }
        )
    }
}

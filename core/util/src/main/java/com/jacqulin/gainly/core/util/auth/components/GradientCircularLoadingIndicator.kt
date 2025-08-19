package com.jacqulin.gainly.core.util.auth.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GradientCircularLoadingIndicator(
    strokeWidth: Dp = 4.dp,
    progressBrush: Brush,
    trackColor: Color,
    modifier: Modifier = Modifier
) {
    // Анимация вращения
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        ),
        label = ""
    )

    Canvas(modifier = modifier) {
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

        // трек (фон)
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke
        )

        // "кусочек" прогресса, который крутится
        drawArc(
            brush = progressBrush,
            startAngle = rotation - 90f,
            sweepAngle = 90f, // длина дуги
            useCenter = false,
            style = stroke
        )
    }
}

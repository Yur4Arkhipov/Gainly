package com.jacqulin.gainly.core.util.auth.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GradientCircularProgressIndicator(
    progress: Float,
    strokeWidth: Dp = 4.dp,
    progressBrush: Brush,
    trackColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

        // трек (фон прогресса)
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke
        )

        // прогресс (градиент)
        drawArc(
            brush = progressBrush,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = stroke
        )
    }
}

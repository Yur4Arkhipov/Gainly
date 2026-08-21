package com.jacqulin.gainly.feature.history.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor

@Composable
fun TargetProgressBar(
    currentValue: Int,
    targetValue: Int,
    progressColor: Color
) {
    val progress = (currentValue.toFloat() / targetValue.toFloat())
        .coerceIn(0f, 1f)
    Log.d("Progress", "Progress: $progress")

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentValue.toString(),
                style = TextStyle(
                    fontFamily = GainlyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    letterSpacing = 0.sp,
                    color = TextBlackColor.copy(alpha = 0.5f)
                ),
                textAlign = TextAlign.Center
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_target),
                    contentDescription = null
                )
                Text(
                    text = targetValue.toString(),
                    style = TextStyle(
                        fontFamily = GainlyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        letterSpacing = 0.sp,
                        color = TextBlackColor.copy(alpha = 0.5f)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(2.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = GrayBackgroundMain,
                    shape = RoundedCornerShape(100)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(
                        color = progressColor,
                        shape = RoundedCornerShape(100)
                    )
            )
        }
    }
}
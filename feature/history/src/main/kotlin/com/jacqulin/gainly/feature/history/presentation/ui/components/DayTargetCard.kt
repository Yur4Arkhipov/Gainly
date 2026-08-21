package com.jacqulin.gainly.feature.history.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.StepsProgressBarColor
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun DayTargetCard(
    title: String,
    icon: Painter,
    currentValue: Int,
    targetValue: Int,
    progressBarColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(
                width = 170.dp,
                height = 150.dp
            )
            .background(
                color = White,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(10.dp)
    ) {
        Column(

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = GainlyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        letterSpacing = 0.sp,
                        color = TextBlackColor
                    )
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = BottomSheetContainerColor,
                            shape = RoundedCornerShape(100)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = currentValue.toString(),
                style = TextStyle(
                    fontFamily = GainlyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    letterSpacing = 0.sp,
                    color = TextBlackColor
                )
            )

            Spacer(Modifier.height(4.dp))

            TargetProgressBar(
                currentValue = currentValue,
                targetValue = targetValue,
                progressColor = progressBarColor
            )
        }
    }
}

@Preview
@Composable
fun DayTargetCardPreview() {
    GainlyTheme() {
        DayTargetCard(
            title = "Калории",
            icon = painterResource(R.drawable.ic_fire),
            currentValue = 1200,
            progressBarColor = StepsProgressBarColor,
            targetValue = 2000,
        )
    }
}
package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.PrimaryBlue
import com.jacqulin.gainly.core.designsystem.theme.White
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EmptyTrainingsCard(
    selectedDate: LocalDate,
    onChooseAnotherDay: () -> Unit,
    onTodayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locale = Locale.forLanguageTag("ru-RU")

    val formattedDate = selectedDate.format(
        DateTimeFormatter.ofPattern("d MMMM yyyy 'года'", locale)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(
                horizontal = 20.dp,
                vertical = 30.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(
                    color = LightGrayColor,
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "В этот день нет записей",
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Black,
                textAlign = TextAlign.Center
            ),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Здесь появятся тренировки и активность\nза $formattedDate.",
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Gray,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = onChooseAnotherDay,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = Black
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp
            )
        ) {
            Text(
                text = "Выбрать другой день",
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Или вернитесь к сегодняшнему дню",
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontSize = 14.sp,
                color = Gray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.clickable(
                onClick = onTodayClick
            )
        )
    }
}

@Preview
@Composable
private fun EmptyTrainingsCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        EmptyTrainingsCard(
            selectedDate = LocalDate.of(2024, 4, 17),
            onChooseAnotherDay = {},
            onTodayClick = {}
        )
    }
}

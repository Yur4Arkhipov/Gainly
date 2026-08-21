package com.jacqulin.gainly.feature.history.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily

@Composable
fun HistoryTopBar(
    onCalendarClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append("История ")

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append("тренировок")
                    }
                },
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                    color = Black
                ),
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onCalendarClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = "Выбрать дату"
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        CalendarSection()
    }
}
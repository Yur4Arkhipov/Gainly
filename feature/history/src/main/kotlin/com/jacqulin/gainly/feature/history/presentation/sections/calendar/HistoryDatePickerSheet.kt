package com.jacqulin.gainly.feature.history.presentation.sections.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDatePickerSheet(
    selectedDate: LocalDate,
    workoutDates: Set<LocalDate> = emptySet(),
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    var temporarySelectedDate by rememberSaveable {
        mutableStateOf(selectedDate.toString())
    }

    val selected = LocalDate.parse(temporarySelectedDate)

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = White,
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        sheetGesturesEnabled = false,
        dragHandle = null
    ) {
        HistoryDatePickerSheetContent(
            selectedDate = selected,
            workoutDates = workoutDates,
            onDateSelected = {
                temporarySelectedDate = it.toString()
            },
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun HistoryDatePickerSheetContent(
    selectedDate: LocalDate,
    workoutDates: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Выбрать дату",
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = 24.sp,
                    lineHeight = 32.sp
                )
            )

            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Закрыть",
                tint = Black,
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(LightGrayColor, CircleShape)
                    .clickable { onDismiss() }
            )
        }

        HistoryMonthCalendar(
            selectedDate = selectedDate,
            workoutDates = workoutDates,
            onDateSelected = onDateSelected
        )

        Button(
            onClick = { onConfirm(selectedDate) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = White
            )
        ) {
            Text(
                text = "Открыть день",
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 380
)
@Composable
private fun HistoryDatePickerSheetContentPreview() {
    HistoryDatePickerSheetContent(
        selectedDate = LocalDate.of(2026, 11, 21),
        workoutDates = setOf(
            LocalDate.of(2026, 11, 3),
            LocalDate.of(2026, 11, 8),
            LocalDate.of(2026, 11, 15),
            LocalDate.of(2026, 11, 21),
            LocalDate.of(2026, 11, 25)
        ),
        onDateSelected = {},
        onConfirm = {},
        onDismiss = {}
    )
}

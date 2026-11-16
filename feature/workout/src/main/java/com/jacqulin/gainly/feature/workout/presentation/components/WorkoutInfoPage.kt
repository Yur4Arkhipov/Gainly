package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.feature.workout.presentation.model.CreateWorkoutUiState
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutType

@Composable
fun WorkoutInfoPage(
    state: CreateWorkoutUiState.Content,
    onTypeSelected: (WorkoutType) -> Unit,
    onTitleChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onTimeChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        WorkoutTypeSwitcher(
            selectedType = state.workoutType,
            onTypeSelected = onTypeSelected,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "Название тренировки",
            value = state.title,
            onValueChange = onTitleChanged,
            textColor = state.selectedColor,
            showEditIcon = true,
            maxLines = 2,
            maxLength = 30,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            CustomTextField(
                label = "Дата",
                value = state.date,
                onValueChange = onDateChanged,
                modifier = Modifier.weight(1.8f)
            )
            CustomTextField(
                label = "Время",
                value = state.time,
                onValueChange = onTimeChanged,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "Заметка",
            value = state.note,
            fontSize = 12.sp,
            onValueChange = onNoteChanged
        )
    }
}

@Preview
@Composable
private fun WorkoutInfoStepPreview() {
    WorkoutInfoPage(
        state = CreateWorkoutUiState.Content(
            title = "Утренняя тренировка",
            date = "15.11.2026",
            time = "18:23",
            note = "Акцент на технику. Отдых между\n" +
                    "подходами — 90 секунд.",
            exercises = emptyList(),
        ),
        onTypeSelected = {},
        onTitleChanged = {},
        onDateChanged = {},
        onTimeChanged = {},
        onNoteChanged = {},
    )
}

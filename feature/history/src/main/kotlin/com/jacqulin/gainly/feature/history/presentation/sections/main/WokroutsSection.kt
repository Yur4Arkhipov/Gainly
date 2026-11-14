package com.jacqulin.gainly.feature.history.presentation.sections.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import com.jacqulin.gainly.feature.history.presentation.sections.workout.WorkoutCard

@Composable
fun WorkoutsSection(
    workouts: List<WorkoutCardUi>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BottomSheetContainerColor,
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 40.dp
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 100.dp
                )
        ) {
            items(workouts) { workout ->
                WorkoutCard(
                    title = "",
                    time = "",
                    onClick = {

                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun WorkoutSectionPreview() {
    GainlyTheme {
        WorkoutsSection(
            workouts = listOf(
                WorkoutCardUi()
            )
        )
    }
}

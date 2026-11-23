package com.jacqulin.gainly.feature.friends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.extensions.fadeScrollableVerticalEdges
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.domain.model.workout.Workout

@Composable
fun TrainingSection(
    trainings: List<Workout>,
) {
    if (trainings.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Тренировок нет",
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = LightGrayColor,
                    fontSize = 16.sp
                )
            )
        }

    } else {
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            overscrollEffect = null,
            modifier = Modifier
                .fadeScrollableVerticalEdges(
                    listState = listState,
                    fadeLength = 24.dp
                )
        ) {
            items(
                items = trainings,
            ) { workout ->
                WorkoutCard(
                    workoutTitle = workout.title,
                    exercises = workout.exercises
                )
            }
        }
    }
}

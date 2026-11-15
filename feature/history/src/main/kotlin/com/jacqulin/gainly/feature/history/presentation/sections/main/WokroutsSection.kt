package com.jacqulin.gainly.feature.history.presentation.sections.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.feature.history.getMockTrainingCards
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import com.jacqulin.gainly.feature.history.presentation.sections.goals.GoalsSection
import com.jacqulin.gainly.feature.history.presentation.sections.workout.TrainingSection
import java.time.LocalDate

@Composable
fun WorkoutsSection(
    workouts: List<WorkoutCardUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(
                    top = 20.dp,
                    bottom = 100.dp
                )
        ) {
            item {
                GoalsSection()
            }

            item {
                Spacer(Modifier.height(20.dp))
            }

            item {
                TrainingSection(
                    trainings = workouts
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
            workouts = getMockTrainingCards(LocalDate.now())
        )
    }
}

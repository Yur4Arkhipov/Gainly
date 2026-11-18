package com.jacqulin.gainly.feature.history.presentation.sections.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import com.jacqulin.gainly.feature.history.getMockTrainingCards
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi
import com.jacqulin.gainly.feature.history.presentation.sections.workout.TrainingSection

@Composable
fun WorkoutsSection(
    workouts: List<WorkoutCardUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(
                    top = 20.dp,
                    bottom = 100.dp
                )
        ) {
//            GoalsSection()
//
//            Spacer(Modifier.height(20.dp))

            TrainingSection(
                trainings = workouts
            )
        }
    }
}
//
//@Preview
//@Composable
//fun WorkoutSectionPreview() {
//    GainlyTheme {
//        WorkoutsSection(
////            workouts = getMockTrainingCards(LocalDate.now())
//        )
//    }
//}

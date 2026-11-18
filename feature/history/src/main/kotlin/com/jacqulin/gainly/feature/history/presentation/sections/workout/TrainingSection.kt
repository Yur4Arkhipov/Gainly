package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.feature.history.presentation.components.InfoRow
import com.jacqulin.gainly.feature.history.presentation.model.WorkoutCardUi

@Composable
fun TrainingSection(
    trainings: List<WorkoutCardUi>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        InfoRow(title = "Тренировки за сутки")

//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            BalancedTrainingCards(
//                trainingCards = trainings
//            )
//        }

        trainings.forEach { workout ->
            WorkoutCard(
                workoutrTitle = workout.title,
                exercises = workout.exercises,
                onClick = { }
            )
        }
    }
}

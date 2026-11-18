package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.feature.history.presentation.model.ExerciseInfoUi
import com.jacqulin.gainly.feature.history.presentation.model.SetInfoUi

@Composable
fun WorkoutCard(
    workoutrTitle: String,
    exercises: List<ExerciseInfoUi>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = workoutrTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF202124)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${exercises.size} упражн.",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF777777)
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color(0xFF777777),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFEEEEEE))

            exercises.forEachIndexed { index, exercise ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = exercise.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF303030)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        exercise.sets.forEach { set ->
                            Surface(
                                color = Color(0xFFF3F4F6),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (set.isOwnWeight) "${set.reps} повт."
                                    else "${set.reps} повт. × ${set.weight} кг",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF454545)
                                )
                            }
                        }
                    }
                }

                if (index != exercises.lastIndex) {
                    HorizontalDivider(
                        color = Color(0xFFF0F0F0)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutCardPreview() {
    GainlyTheme {
        WorkoutCard(
            workoutrTitle = "Тренировка ног",
            exercises = listOf(
                ExerciseInfoUi(
                    title = "Приседания со штангой",
                    sets = listOf(
                        SetInfoUi(reps = 12, weight = 60, isOwnWeight = false),
                        SetInfoUi(reps = 10, weight = 80, isOwnWeight = false),
                        SetInfoUi(reps = 8,  weight = 100, isOwnWeight = false)
                    )
                ),
                ExerciseInfoUi(
                    title = "Жим ногами",
                    sets = listOf(
                        SetInfoUi(reps = 15, weight = 120, isOwnWeight = false),
                        SetInfoUi(reps = 12, weight = 140, isOwnWeight = false)
                    )
                ),
                ExerciseInfoUi(
                    title = "Выпады",
                    sets = listOf(
                        SetInfoUi(reps = 12, weight = 0, isOwnWeight = true)
                    )
                )
            ),
            onClick = {}
        )
    }
}

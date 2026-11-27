package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.Red
import com.jacqulin.gainly.feature.history.presentation.components.AppDropdownMenu
import com.jacqulin.gainly.feature.history.presentation.components.AppDropdownMenuItem
import com.jacqulin.gainly.feature.history.presentation.model.ExerciseInfoUi
import com.jacqulin.gainly.feature.history.presentation.model.SetInfoUi

@Composable
fun WorkoutCard(
    workoutTitle: String,
    exercises: List<ExerciseInfoUi>,
    onDeleteClick: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }

    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "arrowRotation"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = workoutTitle,
                        style = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 20.sp,
                            color = Black
                        )
                    )

                    Text(
                        text = "${exercises.size} упражнения",
                        style = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 16.sp,
                            color = GrayTextColor
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_down),
                        contentDescription = if (expanded) "Свернуть" else "Развернуть",
                        tint = GrayIconColor,
                        modifier = Modifier
                            .size(22.dp)
                            .graphicsLayer { rotationZ = arrowRotation }
                    )

                    Box {
                        IconButton(
                            onClick = { menuExpanded = true },
                            modifier = Modifier.size(22.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more),
                                contentDescription = "Дополнительные действия",
                                tint = GrayIconColor
                            )
                        }

                        AppDropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            AppDropdownMenuItem(
                                text = "Удалить тренировку",
                                textColor = Red,
                                onClick = {
                                    menuExpanded = false
                                    onDeleteClick()
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = tween(220)) + fadeIn(tween(220)),
                exit = shrinkVertically(animationSpec = tween(180)) + fadeOut(tween(180))
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    HorizontalDivider(color = LightGrayColor)

                    exercises.forEachIndexed { index, exercise ->
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = exercise.title,
                                style = TextStyle(
                                    fontFamily = GoogleSansFontFamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 16.sp,
                                    color = Black
                                )
                            )

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                exercise.sets.forEach { set ->
                                    Surface(
                                        color = LightGrayColor,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = if (set.isOwnWeight) "${set.reps} повт."
                                            else "${set.reps} повт. × ${set.weight} кг",
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = GrayTextColor
                                        )
                                    }
                                }
                            }
                        }

                        if (index != exercises.lastIndex) {
                            HorizontalDivider(color = LightGrayColor)
                        }
                    }
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
            workoutTitle = "Тренировка ног",
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
            onDeleteClick = {}
        )
    }
}

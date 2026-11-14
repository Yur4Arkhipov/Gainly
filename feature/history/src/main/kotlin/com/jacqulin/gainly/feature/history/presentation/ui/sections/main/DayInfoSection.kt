package com.jacqulin.gainly.feature.history.presentation.ui.sections.main

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
import com.jacqulin.gainly.feature.history.presentation.ui.sections.workout.WorkoutCard

/*@Composable
fun DayInfoSection(
    trainingCards: List<WorkoutCardUi>,
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
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 100.dp
                )
        ) {
            item {
                ExpandableInfoRow(
//                    title = "Цели на сутки"
                    title = "Скоро",
                    hasDevelopingStatus = true
                ) {
                    Spacer(Modifier.height(10.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            GoalCard(
                                title = "Калории",
                                icon = painterResource(R.drawable.ic_fire),
                                currentValue = 500,
                                targetValue = 2000,
                                progressBarColor = CaloriesProgressBarColor,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        item {
                            GoalCard(
                                title = "Шаги",
                                icon = painterResource(R.drawable.ic_steps),
                                currentValue = 12000,
                                targetValue = 20000,
                                progressBarColor = StepsProgressBarColor,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        item {
                            GoalCard(
                                title = "Вода",
                                icon = painterResource(R.drawable.ic_water),
                                currentValue = 200,
                                targetValue = 2000,
                                progressBarColor = WaterProgressBarColor,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
            }

            item {
                ExpandableInfoRow(
                    title = "Тренировки за сутки"
                ) {
                    Spacer(Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BalancedTrainingCards(
                            trainingCards = trainingCards
                        )
                    }
                }
            }
        }
    }
}*/
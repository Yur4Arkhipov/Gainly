package com.jacqulin.gainly.feature.history.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.BottomSheetContainerColor
import com.jacqulin.gainly.core.designsystem.theme.CaloriesProgressBarColor
import com.jacqulin.gainly.core.designsystem.theme.StepsProgressBarColor
import com.jacqulin.gainly.core.designsystem.theme.WaterProgressBarColor
import com.jacqulin.gainly.feature.history.presentation.model.TrainingCardModel
import com.jacqulin.gainly.feature.history.presentation.ui.goals.GoalCard
import com.jacqulin.gainly.feature.history.presentation.ui.training.BalancedTrainingCards
import com.jacqulin.gainly.feature.history.presentation.ui.components.ExpandableInfoRow

@Composable
fun DayInfoSection(
    trainingCards: List<TrainingCardModel>,
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
}
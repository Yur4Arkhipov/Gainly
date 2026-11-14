package com.jacqulin.gainly.feature.history.presentation.sections.main

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
package com.jacqulin.gainly.feature.history.presentation.sections.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.CaloriesProgressBarColor
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.StepsProgressBarColor
import com.jacqulin.gainly.core.designsystem.theme.WaterProgressBarColor
import com.jacqulin.gainly.feature.history.presentation.components.InfoRow

@Composable
fun GoalsSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        InfoRow(title = "Цели на сутки")

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
                )
            }
            item {
                GoalCard(
                    title = "Шаги",
                    icon = painterResource(R.drawable.ic_steps),
                    currentValue = 12000,
                    targetValue = 20000,
                    progressBarColor = StepsProgressBarColor,
                )
            }
            item {
                GoalCard(
                    title = "Вода",
                    icon = painterResource(R.drawable.ic_water),
                    currentValue = 200,
                    targetValue = 2000,
                    progressBarColor = WaterProgressBarColor,
                )
            }
        }
    }
}

@Preview
@Composable
fun GoalsSectionPreview() {
    GainlyTheme {
        GoalsSection()
    }
}

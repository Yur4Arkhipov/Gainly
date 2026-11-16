package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.PrimaryBlue
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.presentation.model.WorkoutType

@Composable
fun WorkoutTypeSwitcher(
    selectedType: WorkoutType,
    onTypeSelected: (WorkoutType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(White),
    ) {
        WorkoutTypeSwitcherItem(
            label = "Силовая",
            isSelected = selectedType == WorkoutType.Strength,
            onClick = { onTypeSelected(WorkoutType.Strength) },
            modifier = Modifier.weight(1f)
        )
        WorkoutTypeSwitcherItem(
            label = "Кардио",
            isSelected = selectedType == WorkoutType.Cardio,
            onClick = { onTypeSelected(WorkoutType.Cardio) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun WorkoutTypeSwitcherItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) PrimaryBlue else White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) White else Black,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun WorkoutTypeSwitcherStrengthPreview() {
    WorkoutTypeSwitcher(
        selectedType = WorkoutType.Strength,
        onTypeSelected = {}
    )
}

@Preview
@Composable
private fun WorkoutTypeSwitcherCardioPreview() {
    WorkoutTypeSwitcher(
        selectedType = WorkoutType.Cardio,
        onTypeSelected = {}
    )
}

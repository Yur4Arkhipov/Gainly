package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.LightBlackTextColor
import com.jacqulin.gainly.core.designsystem.theme.UnselectedAddWorkoutItem
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun BodyweightToggleBlock(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    selectedColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(UnselectedAddWorkoutItem)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Свой вес",
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = LightBlackTextColor
            )
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = selectedColor,
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = White,
                uncheckedTrackColor = UnselectedAddWorkoutItem,
                uncheckedBorderColor = Color(0xFFE4E4EC)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyweightToggleBlock() {
    var isBodyweightEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BodyweightToggleBlock(
            isChecked = isBodyweightEnabled,
            selectedColor = Color.Blue,
            onCheckedChange = { newState ->
                isBodyweightEnabled = newState
            }
        )
    }
}

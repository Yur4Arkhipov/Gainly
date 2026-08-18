package com.jacqulin.gainly.feature.workout.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.SaveWorkoutButtonContainerColor
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun SaveButton(
    onClick: () -> Unit,
    text: String = "Сохранить",
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SaveWorkoutButtonContainerColor,
            contentColor = White
        )
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            ),
            color = White
        )
    }
}
package com.jacqulin.gainly.feature.workout.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily

@Composable
fun AddButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
    }
}
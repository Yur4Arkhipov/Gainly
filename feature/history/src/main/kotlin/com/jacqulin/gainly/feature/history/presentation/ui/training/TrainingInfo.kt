package com.jacqulin.gainly.feature.history.presentation.ui.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.feature.history.presentation.model.TrainingInfo

@Composable
fun TrainingInfo(
    title: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = GrayBackgroundMain,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Black,
                fontFamily = GoogleSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.sp
            )
        )

    }

    if (isExpanded) {
        content()
    }
}

@Preview
@Composable
fun TrainingInfoPreview() {
    GainlyTheme {
        TrainingInfo(
            title = "Подтягивания",
            isExpanded = true
        ) {
            TrainingInfoExplain(
                trainingInfo = TrainingInfo(
                    title = "fdsf",
                    text = "fsdf"
                )
            )
        }
    }
}
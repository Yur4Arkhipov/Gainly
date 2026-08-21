package com.jacqulin.gainly.feature.history.presentation.ui.training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.feature.history.presentation.model.TrainingInfo

@Composable
fun TrainingInfoExplain(
    trainingInfo: TrainingInfo
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_repeats),
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = trainingInfo.title,
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_weight),
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = trainingInfo.text,
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )
            )
        }
    }
}
package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.LightRedColor
import com.jacqulin.gainly.core.designsystem.theme.PrimaryBlue
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun WorkoutErrorContent(
    errorCause: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = null,
            tint = LightRedColor,
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = "Не удалось загрузить тренировки",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Black,
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = "Причина: $errorCause",
            style = TextStyle(
                fontSize = 13.sp,
                color = GrayTextColor,
                textAlign = TextAlign.Center
            )
        )

        Button(
            onClick = onRetry,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = White
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_replay),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(Modifier.width(8.dp))

            Text("Попробовать снова")
        }
    }
}

@Preview(name = "Короткая ошибка", showBackground = true)
@Composable
fun WorkoutErrorContentPreview_Short() {
    GainlyTheme {
        WorkoutErrorContent(
            errorCause = "Нет подключения к интернету",
            onRetry = {}
        )
    }
}

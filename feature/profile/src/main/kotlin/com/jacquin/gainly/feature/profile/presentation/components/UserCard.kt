package com.jacquin.gainly.feature.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.BlackColor
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun UserCard(
    username: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(White)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = username,
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = BlackColor,
                lineHeight = 30.sp
            )
        )
    }
}

@Preview
@Composable
fun UserCardPreview() {
    GainlyTheme {
        UserCard(
            username = "Username"
        )
    }
}

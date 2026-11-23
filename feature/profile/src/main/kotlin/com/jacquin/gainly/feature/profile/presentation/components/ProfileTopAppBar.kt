package com.jacquin.gainly.feature.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.theme.BlackColor
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily

@Composable
fun ProfileTopAppBar(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Профиль",
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 32.sp,
                color = BlackColor
            )
        )
    }
}

@Preview
@Composable
fun ProfileTopAppBarPreview() {
    GainlyTheme {
        ProfileTopAppBar()
    }
}

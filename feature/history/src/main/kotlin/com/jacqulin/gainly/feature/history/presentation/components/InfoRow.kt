package com.jacqulin.gainly.feature.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun InfoRow(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = GainlyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                letterSpacing = 0.sp,
                color = TextBlackColor
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
        IconButton(
            onClick = { },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = LightGrayColor,
                contentColor = Black
            ),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = GrayIconColor,
            )
        }
    }
}

@Preview
@Composable
fun InfoRowPreview() {
    GainlyTheme {
        InfoRow(
            title = "Цели на сутки"
        )
    }
}

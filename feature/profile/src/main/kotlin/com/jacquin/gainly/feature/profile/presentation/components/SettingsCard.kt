package com.jacquin.gainly.feature.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.BlackColor
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    onAccountClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onConfidentialityClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Настройки и помощь",
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = BlackColor
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(White)
                .padding(10.dp)
        ) {
            SettingsItemRow(
                icon = painterResource(R.drawable.ic_profile_card),
                title = "Аккаунт",
                subtitle = "Вход и удаление профиля",
                onClick = onAccountClick
            )
            SettingsItemRow(
                icon = painterResource(R.drawable.ic_notifications),
                title = "Уведомления",
                onClick = onNotificationClick
            )
            SettingsItemRow(
                icon = painterResource(R.drawable.ic_confidentiality),
                title = "Конфиденциальность",
                onClick = onConfidentialityClick
            )
            SettingsItemRow(
                icon = painterResource(R.drawable.ic_help),
                title = "Помощь и обратная связь",
                onClick = onHelpClick
            )
        }
    }
}

@Composable
private fun SettingsItemRow(
    icon: Painter,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = GrayIconColor,
            modifier = Modifier.size(20.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = BlackColor
                )
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        color = GrayTextColor
                    )
                )
            }
        }
        Icon(
            painter = painterResource(R.drawable.ic_right),
            contentDescription = null,
            tint = GrayTextColor,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Preview
@Composable
fun SettingsCardPreview() {
    GainlyTheme {
        SettingsCard(
            onAccountClick = {},
            onNotificationClick = {},
            onConfidentialityClick = {},
            onHelpClick = {}
        )
    }
}

package com.jacqulin.gainly.feature.friends.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.PinkColor
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun AddFriendsButton(
    requestsCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add_friends),
            contentDescription = "Добавить друзей",
            tint = GrayIconColor,
            modifier = Modifier.size(24.dp)
        )

        if (requestsCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(16.dp)
                    .background(PinkColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (requestsCount > 99) "99+" else requestsCount.toString(),
                    color = White,
                    fontSize = 8.sp,
                    lineHeight = 8.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
    }
}

@Preview(name = "Без заявок", showBackground = true)
@Composable
fun AddFriendsButtonPreview_NoRequests() {
    GainlyTheme {
        Box(Modifier.padding(24.dp)) {
            AddFriendsButton(
                requestsCount = 0,
                onClick = {}
            )
        }
    }
}

@Preview(name = "5 заявок", showBackground = true)
@Composable
fun AddFriendsButtonPreview_FiveRequests() {
    GainlyTheme {
        Box(Modifier.padding(24.dp)) {
            AddFriendsButton(
                requestsCount = 5,
                onClick = {}
            )
        }
    }
}


@Preview(name = "100+ заявок", showBackground = true)
@Composable
fun AddFriendsButtonPreview_ManyRequests() {
    GainlyTheme {
        Box(Modifier.padding(24.dp)) {
            AddFriendsButton(
                requestsCount = 128,
                onClick = {}
            )
        }
    }
}


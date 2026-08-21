package com.jacqulin.gainly.feature.history.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun ExpandableInfoRow(
    title: String,
    hasDevelopingStatus: Boolean = false,
    content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(
                    color = White,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 5.dp)
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
                    color = if (hasDevelopingStatus) {
                        TextBlackColor.copy(alpha = 0.2f)
                    } else {
                        TextBlackColor
                    }
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
            IconButton(
                onClick = {
                    isExpanded = !isExpanded
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = GrayBackgroundMain,
                    contentColor = GrayIconColor
                ),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            ) {
                if (isExpanded) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_top),
                        contentDescription = "Скрыть меню",
                        tint = GrayIconColor
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_bottom),
                        contentDescription = "Раскрыть меню",
                        tint = GrayIconColor
                    )
                }
            }
        }
    }

    if (isExpanded) {
        content()
    }
}

@Preview
@Composable
fun ExpandableInfoRowPreview() {
    GainlyTheme() {
        ExpandableInfoRow(
            title = "Цели на сутки",
            hasDevelopingStatus = false,
        ) {

        }
    }
}
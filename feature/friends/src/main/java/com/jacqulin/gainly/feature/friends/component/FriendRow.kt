package com.jacqulin.gainly.feature.friends.component

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.GrayText
import com.jacqulin.gainly.core.designsystem.theme.GreenCheckmark
import com.jacqulin.gainly.core.designsystem.theme.RedCheckmark
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun FriendRow(
    name: String,
    isRequestSent: Boolean = false,
    isAddFriendsScreen: Boolean = true,
    isPendingUser: Boolean = false,
    onAddClick: () -> Unit = { },
    onAcceptClick: () -> Unit = { },
    onRejectClick: () -> Unit = { },
    onFriendClick: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(32.dp)
            )
            .clickable { onFriendClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(

            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.W500,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "@ilusha",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        letterSpacing = 0.sp,
                        color = GrayText
                    )
                )
            }
        }

        if (isAddFriendsScreen && !isPendingUser) {
            IconButton(
                onClick = onAddClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = GrayBackgroundMain,
                    contentColor = GrayIconColor
                ),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = if (isRequestSent) painterResource(R.drawable.ic_checkmark)
                    else painterResource(R.drawable.ic_add_friends),
                    contentDescription = "Добавить в друзья",
                    tint = if (isRequestSent) GreenCheckmark
                    else GrayIconColor
                )
            }
        }
        else if (isPendingUser) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onRejectClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = GrayBackgroundMain,
                        contentColor = RedCheckmark
                    ),
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_reject),
                        contentDescription = "Отклонить",
                        tint = RedCheckmark
                    )
                }

                IconButton(
                    onClick = onAcceptClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = GrayBackgroundMain,
                        contentColor = GreenCheckmark
                    ),
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_confirm),
                        contentDescription = "Принять",
                        tint = GreenCheckmark
                    )
                }
            }
        }
        else {
            IconButton(
                onClick = { },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = GrayBackgroundMain,
                    contentColor = GrayIconColor
                ),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_message),
                    contentDescription = "Сообщение",
                    tint = GrayIconColor
                )
            }
        }
    }
}
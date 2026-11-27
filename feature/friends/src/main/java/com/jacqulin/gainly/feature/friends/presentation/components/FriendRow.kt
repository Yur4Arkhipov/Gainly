package com.jacqulin.gainly.feature.friends.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.GreenCheckmark
import com.jacqulin.gainly.core.designsystem.theme.OrangeColor
import com.jacqulin.gainly.core.designsystem.theme.RedCheckmark
import com.jacqulin.gainly.core.designsystem.theme.White

@Composable
fun FriendRow(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    name: String,
    isRequestSent: Boolean = false,
    isAddFriendsScreen: Boolean = true,
    isPendingUser: Boolean = false,
    onAddClick: () -> Unit = { },
    onAcceptClick: () -> Unit = { },
    onRejectClick: () -> Unit = { },
    onFriendClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .clickable { onFriendClick() }
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
/*
            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_message),
                error = painterResource(R.drawable.ic_message)
            )
*/
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = name,
                style = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = FontWeight.W500,
                    fontStyle = FontStyle.Normal,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (isAddFriendsScreen && !isPendingUser) {
            IconButton(
                onClick = onAddClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = GrayBackgroundMain,
                    contentColor = GrayIconColor
                ),
                enabled = !isRequestSent,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = if (isRequestSent)
                        painterResource(R.drawable.ic_pending)
                    else
                        painterResource(R.drawable.ic_add_friends),
                    contentDescription = "Добавить в друзья",
                    tint = if (isRequestSent) OrangeColor else GrayIconColor
                )
            }
        } else if (isAddFriendsScreen) {
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
    }
}

@Preview(name = "Add friends", showBackground = true, widthDp = 360)
@Composable
fun FriendRowPreview_AddFriends() {
    GainlyTheme {
        Box(Modifier.padding(16.dp)) {
            FriendRow(
                avatarUrl = null,
                name = "Иван Иванов",
                isAddFriendsScreen = true,
                isRequestSent = false,
                isPendingUser = false
            )
        }
    }
}

@Preview(name = "Request sent", showBackground = true, widthDp = 360)
@Composable
fun FriendRowPreview_RequestSent() {
    GainlyTheme {
        Box(Modifier.padding(16.dp)) {
            FriendRow(
                avatarUrl = null,
                name = "Иван Иванов",
                isAddFriendsScreen = true,
                isRequestSent = true
            )
        }
    }
}

@Preview(name = "Pending", showBackground = true, widthDp = 360)
@Composable
fun FriendRowPreview_Pending() {
    GainlyTheme {
        Box(Modifier.padding(16.dp)) {
            FriendRow(
                avatarUrl = null,
                name = "Мария Петрова",
                isPendingUser = true
            )
        }
    }
}

@Preview(name = "Regular friend", showBackground = true, widthDp = 360)
@Composable
fun FriendRowPreview_RegularFriend() {
    GainlyTheme {
        Box(Modifier.padding(16.dp)) {
            FriendRow(
                avatarUrl = null,
                name = "Алексей Смирнов",
                isAddFriendsScreen = false,
                isPendingUser = false
            )
        }
    }
}

@Preview(name = "Long name", showBackground = true, widthDp = 360)
@Composable
fun FriendRowPreview_LongName() {
    GainlyTheme {
        Box(Modifier.padding(16.dp)) {
            FriendRow(
                avatarUrl = null,
                name = "Константин Константинопольский-Великолепный",
                isAddFriendsScreen = true
            )
        }
    }
}

@Preview(name = "Long name + pending", showBackground = true, widthDp = 360)
@Composable
fun FriendRowPreview_LongNamePending() {
    GainlyTheme {
        Box(Modifier.padding(16.dp)) {
            FriendRow(
                avatarUrl = null,
                name = "Константин Константинопольский-Великолепный",
                isPendingUser = true
            )
        }
    }
}

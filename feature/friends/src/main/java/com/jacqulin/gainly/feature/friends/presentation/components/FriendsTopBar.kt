package com.jacqulin.gainly.feature.friends.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.LightGrayColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.core.util.auth.components.CustomOutlinedTextField

@Composable
fun FriendsTopBar(
    text: String,
    textStyle: TextStyle,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    showBackButton: Boolean,
    showAddFriendsButton: Boolean,
    onAddFriendsClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
            .padding(horizontal = 10.dp)
            .padding(top = 6.dp)
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = textStyle
            )

            if (showAddFriendsButton) {
                AddFriendsButton(
                    requestsCount = 3,
                    onClick = onAddFriendsClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            if (showBackButton) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_simple),
                    contentDescription = "Назад",
                    tint = GrayIconColor,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onBackClick
                        )
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomOutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                shape = RoundedCornerShape(28.dp),
                label = "Поиск друзей",
                leadingIcon = painterResource(R.drawable.ic_search),
                trailingIcon = if (searchQuery.isNotEmpty()) painterResource(R.drawable.ic_close) else null,
                onTrailingIconClick = { onSearchQueryChange("") },
                imeAction = ImeAction.Search,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun FriendsTopBarPreview() {
    GainlyTheme {
        Column(modifier = Modifier.background(LightGrayColor)) {
            FriendsTopBar(
                text = "Друзья",
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                ),
                searchQuery = "",
                onSearchQueryChange = {},
                showBackButton = true,
                showAddFriendsButton = true,
                onAddFriendsClick = {},
                onBackClick = {}
            )
        }
    }
}

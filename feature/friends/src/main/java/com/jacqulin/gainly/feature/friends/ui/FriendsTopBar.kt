package com.jacqulin.gainly.feature.friends.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R
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
            .padding(horizontal = 8.dp)
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
                IconButton(
                    onClick = onAddFriendsClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_friends),
                        contentDescription = "Add friends",
                    )
                }
            }

            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "Go back",
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
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

        Spacer(Modifier.height(16.dp))
    }
}
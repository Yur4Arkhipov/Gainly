package com.jacqulin.gainly.feature.friends.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R

@Composable
fun FriendsTopBar(
    text: String,
    showBackButton: Boolean,
    showAddFriendsButton: Boolean,
    onAddFriends: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
        if (showAddFriendsButton) {
            IconButton(
                onClick = onAddFriends,
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
}
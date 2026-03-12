package com.jacqulin.gainly.feature.friends.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jacqulin.gainly.feature.friends.viewmodel.AddFriendsUiState
import com.jacqulin.gainly.feature.friends.viewmodel.AddFriendsViewModel
import com.jacqulin.gainly.feature.friends.viewmodel.FriendRequestUiModel

@Composable
fun AddFriendsScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddFriendsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
    ) {
        FriendsTopBar(
            text = "Добавление друзей",
            showBackButton = showBackButton,
            showAddFriendsButton = false,
            onAddFriends = { },
            onBackClick = onBackClick
        )

        PendingRequestsBlock(
            state = uiState,
            onToggleExpansion = { viewModel.toggleExpansion() }
        )
    }
}

@Composable
private fun PendingRequestsBlock(
    state: AddFriendsUiState,
    onToggleExpansion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pending = state.pendingRequests
    if (pending.isEmpty()) {
        Text(
            text = "Экран добавления друзей",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Medium
        )
        return
    }

    val visibleItems = if (state.isExpanded) pending else pending.take(AddFriendsViewModel.PREVIEW_LIMIT)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(visibleItems, key = { it.id }) { request ->
            FriendRequestCard(request)
        }
    }

    if (pending.size > AddFriendsViewModel.PREVIEW_LIMIT) {
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onToggleExpansion
            ) {
                Text(
                    text = if (state.isExpanded) "Свернуть" else "Показать ещё ${pending.size - visibleItems.size}",
                )
            }
        }
    }

    if (!state.isExpanded) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Экран добавления друзей",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun FriendRequestCard(
    request: FriendRequestUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column{
                Text(text = request.username, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Общие друзья: ${request.mutualFriends}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /* TODO: Handle accept friend request */ },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Accept",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = { /* TODO: Handle decline friend request */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Decline",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
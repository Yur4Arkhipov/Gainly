package com.jacqulin.gainly.feature.friends.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.feature.friends.component.FriendRow
import com.jacqulin.gainly.feature.friends.component.FriendsTopBar
import com.jacqulin.gainly.feature.friends.viewmodel.AddFriendsViewModel

@Composable
fun AddFriendsScreen(
    onBackClick: () -> Unit,
    viewModel: AddFriendsViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val pendingUsers by viewModel.pendingUsers.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadPendingRequests()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroundMain)
    ) {
        FriendsTopBar(
            text = "Найти друга",
            textStyle = TextStyle(
                fontFamily = GainlyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                letterSpacing = 0.sp,
                color = TextBlackColor
            ),
            showBackButton = true,
            showAddFriendsButton = false,
            searchQuery = searchQuery,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onAddFriendsClick = { },
            onBackClick = onBackClick,
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            if (searchQuery.isNotEmpty() && searchResults.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults) { user ->
                        FriendRow(
                            name = user.username,
                            isRequestSent = user.isRequestSent,
                            isAddFriendsScreen = true,
                            isPendingUser = false,
                            onAddClick = {
                                viewModel.sendFriendRequest(user.username)
                                Log.d("Friends", user.username)
                            }
                        )
                    }
                }
            } else if (searchQuery.isNotEmpty() && searchResults.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Пользователей не найдено")
                }
            } else if (searchQuery.isEmpty() && pendingUsers.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(pendingUsers) { user ->
                            FriendRow(
                                name = user.username,
                                isRequestSent = false,
                                isAddFriendsScreen = false,
                                isPendingUser = true,
                                onAcceptClick = {
                                    viewModel.acceptPendingUser(user.username, user.friendshipId)
                                    Log.d("Friends", "Accepted: ${user.username}")
                                },
                                onRejectClick = {
                                    viewModel.rejectPendingUser(user.username, user.friendshipId)
                                    Log.d("Friends", "Rejected: ${user.username}")
                                }
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Введите имя пользователя для поиска")
                }
            }
        }
    }
}
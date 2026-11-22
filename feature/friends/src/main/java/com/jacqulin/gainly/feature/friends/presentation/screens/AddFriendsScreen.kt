package com.jacqulin.gainly.feature.friends.presentation.screens

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.feature.friends.presentation.components.FriendRow
import com.jacqulin.gainly.feature.friends.presentation.components.FriendsTopBar
import com.jacqulin.gainly.feature.friends.presentation.model.AddFriendsEffect
import com.jacqulin.gainly.feature.friends.presentation.model.AddFriendsEvent
import com.jacqulin.gainly.feature.friends.presentation.model.AddFriendsUiState
import com.jacqulin.gainly.feature.friends.presentation.viewmodels.AddFriendsViewModel

@Composable
fun AddFriendsScreen(
    onBackClick: () -> Unit,
    viewModel: AddFriendsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is AddFriendsEffect.NavigateToFriends -> {
                    onBackClick()
                }

                is AddFriendsEffect.ShowSnackbar ->  {
                    snackbarHostState.showSnackbar(
                        message = effect.message
                    )
                }
            }
        }
    }

    AddFriendsScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddFriendsScreenContent(
    state: AddFriendsUiState,
    modifier: Modifier = Modifier,
    onEvent: (AddFriendsEvent) -> Unit,
) {
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
            searchQuery = state.searchQuery,
            onSearchQueryChange = { query ->
                onEvent(AddFriendsEvent.SearchQueryChanged(query))
            },
            onAddFriendsClick = { },
            onBackClick = {
                onEvent(AddFriendsEvent.OnBackClicked)
            },
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            if (state.searchQuery.isNotEmpty() && state.searchResult.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.searchResult) { user ->
                        FriendRow(
                            name = user.username,
                            isRequestSent = user.isRequestSent,
                            isAddFriendsScreen = true,
                            isPendingUser = false,
                            onAddClick = {
                                onEvent(AddFriendsEvent.SendFriendRequest(user.userId))
                            }
                        )
                    }
                }
            } else if (state.searchQuery.isNotEmpty() && state.searchResult.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Пользователей не найдено")
                }
            } else if (state.searchQuery.isEmpty() && state.pendingUsers.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.pendingUsers) { user ->
                            FriendRow(
                                name = user.username,
                                isRequestSent = false,
                                isAddFriendsScreen = false,
                                isPendingUser = true,
                                onAcceptClick = {
                                    onEvent(AddFriendsEvent.RespondFriendRequest(user.username, user.friendshipId, true))
                                    Log.d("Friends", "Accepted: ${user.username}")
                                },
                                onRejectClick = {
                                    onEvent(AddFriendsEvent.RespondFriendRequest(user.username, user.friendshipId, false))
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

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.LightBlackTextColor
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.core.designsystem.theme.VeryLightGrayColor
import com.jacqulin.gainly.core.domain.model.friends.UserData
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

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = VeryLightGrayColor,
                    contentColor = LightBlackTextColor,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    ) { innerPadding ->
        AddFriendsScreenContent(
            state = state,
            modifier = Modifier.padding(innerPadding),
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun AddFriendsScreenContent(
    state: AddFriendsUiState,
    modifier: Modifier = Modifier,
    onEvent: (AddFriendsEvent) -> Unit,
) {
    Column(
        modifier = modifier
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
                .padding(horizontal = 10.dp)
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
                                isAddFriendsScreen = true,
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

@Preview(showBackground = true)
@Composable
private fun AddFriendsScreenContentPreview() {
    GainlyTheme {
        AddFriendsScreenContent(
            state = AddFriendsUiState(
                searchQuery = "alex",
                searchResult = listOf(
                    UserData(
                        userId = "1",
                        username = "alex_dev",
                        registrationDate = "12.03.2025",
                        isRequestSent = false
                    ),
                    UserData(
                        userId = "2",
                        username = "alex_fit",
                        registrationDate = "18.05.2025",
                        isRequestSent = true,
                        friendshipId = "request_101"
                    ),
                    UserData(
                        userId = "3",
                        username = "alex_workout",
                        registrationDate = "02.07.2025"
                    )
                )
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddFriendsPendingPreview() {
    GainlyTheme {
        AddFriendsScreenContent(
            state = AddFriendsUiState(
                pendingUsers = listOf(
                    UserData(
                        userId = "1",
                        username = "maria_fit",
                        registrationDate = "",
                        friendshipId = "friendship_101"
                    ),
                    UserData(
                        userId = "2",
                        username = "john_workout",
                        registrationDate = "",
                        friendshipId = "friendship_102"
                    )
                )
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddFriendsEmptyPreview() {
    GainlyTheme {
        AddFriendsScreenContent(
            state = AddFriendsUiState(),
            onEvent = {}
        )
    }
}

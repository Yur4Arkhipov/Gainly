package com.jacqulin.gainly.feature.friends.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.component.FloatingBottomBarScrollPadding
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.feature.friends.presentation.components.FriendRow
import com.jacqulin.gainly.feature.friends.presentation.components.FriendsTopBar
import com.jacqulin.gainly.feature.friends.presentation.model.FriendsEffect
import com.jacqulin.gainly.feature.friends.presentation.model.FriendsEvent
import com.jacqulin.gainly.feature.friends.presentation.model.FriendsUiState
import com.jacqulin.gainly.feature.friends.presentation.viewmodels.FriendsViewModel

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = hiltViewModel(),
    onAddFriendsClick: () -> Unit,
    onFriendClick: (username: String) -> Unit = { }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is FriendsEffect.NavigateToAddFriends -> {
                    onAddFriendsClick()
                }

                is FriendsEffect.ShowSnackbar ->  {

                }
            }
        }
    }

    FriendsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onFriendClick = onFriendClick,
    )
}

@Composable
fun FriendsScreen(
    state: FriendsUiState,
    modifier: Modifier = Modifier,
    onEvent: (FriendsEvent) -> Unit,
    onFriendClick: (username: String) -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBackgroundMain)
    ) {
        FriendsTopBar(
            text = "Список друзей",
            textStyle = TextStyle(
                fontFamily = GainlyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                letterSpacing = 0.sp,
                color = TextBlackColor
            ),
            showBackButton = false,
            showAddFriendsButton = true,
            searchQuery = state.searchQuery,
            onSearchQueryChange = { query ->
                onEvent(FriendsEvent.SearchQueryChanged(query))
            },
            onAddFriendsClick = {
                onEvent(FriendsEvent.AddFriendsClicked)
            },
            onBackClick = { },
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            when {
//                uiState.isSearching && uiState.friends.isEmpty() -> {
//                    CircularProgressIndicator(
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }

                state.friends.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (state.searchQuery.isBlank()) "Друзей нет" else "Никого не найдено",
                            style = TextStyle(
                                fontFamily = GainlyFontFamily,
                                color = GrayTextColor
                            )
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = FloatingBottomBarScrollPadding),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
//                        items(uiState.friends) { friendUiItem ->
//                            FriendRow(
//                                name = friendUiItem.username,
//                                isAddFriendsScreen = false,
//                                onFriendClick = {
//                                    viewModel.onFriendClick(friendUiItem.username, friendUiItem.username)
//                                }
//                            )
//                        }
                        items(state.friends) { friend ->
                            FriendRow(
                                name = friend.username,
                                isAddFriendsScreen = false,
                                onFriendClick = {
                                    onFriendClick(friend.username)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    name = "Friends — пусто"
)
@Composable
fun FriendsScreenEmptyPreview() {
    GainlyTheme {
        FriendsScreen(
            state = FriendsUiState(),
            onEvent = {},
        )
    }
}

@Preview(
    showSystemUi = true,
    name = "Friends — есть друзья"
)
@Composable
fun FriendsScreenPreview() {
    GainlyTheme {
        FriendsScreen(
            state = FriendsUiState(
                friends = listOf(
                    FriendData(userId = "1", username = "Иван Иванов", registrationDate = ""),
                    FriendData(userId = "2", username = "Мария Петрова", registrationDate = ""),
                    FriendData(userId = "3", username = "Alex", registrationDate = ""),
                ),
            ),
            onEvent = {},
        )
    }
}

@Preview(
    showSystemUi = true,
    name = "Friends — ничего не найдено"
)
@Composable
fun FriendsScreenNoResultsPreview() {
    GainlyTheme {
        FriendsScreen(
            state = FriendsUiState(
                searchQuery = "некто",
            ),
            onEvent = {},
        )
    }
}

package com.jacqulin.gainly.feature.friends.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.feature.friends.component.FriendRow
import com.jacqulin.gainly.feature.friends.component.FriendsTopBar
import com.jacqulin.gainly.feature.friends.viewmodel.FriendsViewModel

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = hiltViewModel(),
    onAddFriendsClick: () -> Unit,
    onFriendClick: (friendId: String, friendName: String) -> Unit = { _, _ -> }
) {
//    val context = LocalContext.current
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val friends by viewModel.filteredFriends.collectAsStateWithLifecycle()

//    // Handle effects
//    LaunchedEffect(Unit) {
//        viewModel.effects.collect { effect ->
//            when (effect) {
//                is FriendsEffect.NavigateToFriendWorkouts -> {
//                    onFriendClick(effect.friendId, effect.friendName)
//                }
//
//                is FriendsEffect.ShowSyncSuccess -> {
//                    Toast.makeText(context, "Друзья синхронизированы", Toast.LENGTH_SHORT).show()
//                }
//
//                is FriendsEffect.ShowError -> {
//                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    FriendsScreen(
        searchQuery = searchQuery,
        friends = friends,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onAddFriendsClick = onAddFriendsClick,
        onFriendClick = onFriendClick,
    )
}

@Composable
fun FriendsScreen(
    searchQuery: String,
    friends: List<FriendData>,
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit,
    onAddFriendsClick: () -> Unit,
    onFriendClick: (friendId: String, friendName: String) -> Unit = { _, _ -> },
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
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onAddFriendsClick = onAddFriendsClick,
            onBackClick = { },
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            when {
//                uiState.isSearching && uiState.friends.isEmpty() -> {
//                    CircularProgressIndicator(
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }

                friends.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (searchQuery.isBlank()) "Друзей нет" else "Никого не найдено",
                            style = TextStyle(
                                fontFamily = GainlyFontFamily,
                                color = TextBlackColor
                            )
                        )
                    }
                }

                else -> {
                    LazyColumn(
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
                        items(friends) { friend ->
                            FriendRow(
                                name = friend.username,
                                isAddFriendsScreen = false,
                                onFriendClick = {
                                    onFriendClick(friend.userId, friend.username)
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
            searchQuery = "",
            friends = emptyList(),
            onSearchQueryChange = {},
            onAddFriendsClick = {},
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
            searchQuery = "",
            friends = listOf(
                FriendData(userId = "1", username = "Иван Иванов", registrationDate = ""),
                FriendData(userId = "2", username = "Мария Петрова", registrationDate = ""),
                FriendData(userId = "3", username = "Alex", registrationDate = ""),
            ),
            onSearchQueryChange = {},
            onAddFriendsClick = {},
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
            searchQuery = "некто",
            friends = emptyList(),
            onSearchQueryChange = {},
            onAddFriendsClick = {},
        )
    }
}

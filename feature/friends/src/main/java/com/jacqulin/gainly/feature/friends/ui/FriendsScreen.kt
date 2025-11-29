package com.jacqulin.gainly.feature.friends.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.feature.friends.viewmodel.FriendsViewModel

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = hiltViewModel(),
    onAddFriendsClick: () -> Unit
) {
    val friends by viewModel.friends.collectAsState()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    var searchExpanded by rememberSaveable { mutableStateOf(false) }

    // изменить на запись в локальную бд
    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
    ) {
        FriendsTopBar(
            text = "Список друзей",
            showBackButton = false,
            showAddFriendsButton = true,
            onAddFriends = onAddFriendsClick,
            onBackClick = { },
        )

        Spacer(Modifier.height(10.dp))

        UsersSearchBar(
            searchQuery = viewModel.searchQuery,
            searchResults = searchResults,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            onSendFriendshipRequestClick = { nickname ->
                viewModel.sendFriendRequest(nickname)
            }
        )

        if (!searchExpanded) {

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(friends) { friend ->
                        FriendInfoRow(name = friend.username)
                    }
                }
            }
        }
    }
}

@Composable
fun FriendInfoRow(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = name)
    }
}

@Preview(showBackground = true)
@Composable
fun TopFriendsBarPreview() {
    FriendsTopBar(
        text = "Список друзей",
        showBackButton = false,
        showAddFriendsButton = true,
        onAddFriends = { },
        onBackClick = { }
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun FriendsScreenPreview() {
    FriendsScreen(onAddFriendsClick = {})
}
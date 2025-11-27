package com.jacqulin.gainly.feature.friends.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jacqulin.gainly.feature.friends.viewmodel.AddFriendsViewModel

@Composable
fun AddFriendsScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddFriendsViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        FriendsTopBar(
            text = "Добавление друзей",
            showBackButton = showBackButton,
            showAddFriendsButton = false,
            onAddFriends = { },
            onBackClick = onBackClick
        )

        Spacer(Modifier.height(15.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f).fillMaxSize()
        ) {
            Text("Экран добавления друзей")
        }
    }
}
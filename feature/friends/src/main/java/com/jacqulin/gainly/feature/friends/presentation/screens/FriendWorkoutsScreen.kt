package com.jacqulin.gainly.feature.friends.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.feature.friends.presentation.viewmodels.FriendWorkoutsViewModel

@Composable
fun FriendWorkoutsScreenRoute(
    viewModel: FriendWorkoutsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    FriendWorkoutsScreen()
}

@Composable
fun FriendWorkoutsScreen() {

}

@Preview
@Composable
fun FriendWorkoutsScreenPreview() {
    GainlyTheme {
        FriendWorkoutsScreen()
    }
}

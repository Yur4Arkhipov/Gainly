package com.jacquin.gainly.feature.profile.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacquin.gainly.feature.profile.presentation.components.ExitButton
import com.jacquin.gainly.feature.profile.presentation.components.ProfileTopAppBar
import com.jacquin.gainly.feature.profile.presentation.components.SettingsCard
import com.jacquin.gainly.feature.profile.presentation.components.UserCard
import com.jacquin.gainly.feature.profile.presentation.model.ProfileEffect
import com.jacquin.gainly.feature.profile.presentation.model.ProfileEvent
import com.jacquin.gainly.feature.profile.presentation.model.ProfileUiState
import com.jacquin.gainly.feature.profile.presentation.viewmodel.ProfileViewModel
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.component.FloatingBottomBarScrollPadding
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor

@Composable
fun ProfileScreenRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ProfileEffect.ShowMessage -> Unit
            }
        }
    }
    ProfileScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    modifier: Modifier = Modifier,
    onEvent: (ProfileEvent) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
            .background(GrayBackgroundMain)
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = FloatingBottomBarScrollPadding)
    ) {
        ProfileTopAppBar()

        UserCard(
            username = state.username,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingsCard(
            onAccountClick = {},
            onNotificationClick = {},
            onConfidentialityClick = {},
            onHelpClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        ExitButton(
            isSigningOut = state.isSigningOut,
            onExit = {
                onEvent(ProfileEvent.SignOutClicked)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Версия 0.0.0",
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                lineHeight = 15.sp,
                fontSize = 11.sp,
                color = GrayTextColor
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Profile Screen"
)
@Composable
private fun ProfileScreenPreview() {
    GainlyTheme {
        ProfileScreen(
            state = ProfileUiState(),
            onEvent = {}
        )
    }
}

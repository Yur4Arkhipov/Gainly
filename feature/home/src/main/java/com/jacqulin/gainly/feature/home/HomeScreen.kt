package com.jacqulin.gainly.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.hilt.navigation.compose.hiltViewModel
import com.jacqulin.gainly.core.util.UiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState by viewModel.uiState.collectAsState()

        Text(text = "Home screen")
        Button(
            onClick = { viewModel.logout() }
        ) {
            Text("Logout")
        }

        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> Text("Logout successful!", color = Color.Green)
            is UiState.Error -> Text(
                (uiState as UiState.Error).message,
                color = Red
            )
            else -> Unit
        }
    }
}
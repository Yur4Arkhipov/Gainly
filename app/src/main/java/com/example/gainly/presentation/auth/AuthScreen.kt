package com.example.gainly.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var stateFirstInputField by remember { mutableStateOf("") }
    var stateSecondInputField by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = stateFirstInputField,
            onValueChange = { stateFirstInputField = it },
            label = { Text("Input first field") },
            modifier = modifier.padding(10.dp)
        )
        TextField(
            value = stateSecondInputField,
            onValueChange = { stateSecondInputField = it },
            label = { Text("Input first field") },
            modifier = modifier.padding(10.dp)
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun AutScreenPreview(
    modifier: Modifier = Modifier
) {
    var stateFirstInputField by remember { mutableStateOf("") }
    var stateSecondInputField by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = stateFirstInputField,
            onValueChange = { stateFirstInputField = it },
            label = { Text("Input first field") },
            modifier = modifier.padding(10.dp)
        )
        TextField(
            value = stateSecondInputField,
            onValueChange = { stateSecondInputField = it },
            label = { Text("Input first field") },
            modifier = modifier.padding(10.dp)
        )
        Spacer(Modifier.height(10.dp))
        Text("First state: $stateFirstInputField")
        Text("First state: $stateSecondInputField")
    }
}
package com.jacqulin.gainly.core.util.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginTextField(
    login: String,
    onLoginChange: (String) -> Unit
) {
    OutlinedTextField(
        value = login,
        onValueChange = onLoginChange,
        label = { Text("Email") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
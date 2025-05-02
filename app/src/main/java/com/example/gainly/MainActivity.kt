package com.example.gainly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gainly.ui.theme.GainlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GainlyTheme {
                Scaffold { innerPadding ->
                    App(innerPadding)
                }
            }
        }
    }
}

@Composable
fun App(
    paddingValues: PaddingValues
) {
    Text(
        text = "Hello, Android!",
        modifier = Modifier.padding(paddingValues)
    )
}
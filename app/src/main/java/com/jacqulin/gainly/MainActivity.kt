package com.jacqulin.gainly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jacqulin.gainly.navigation.AuthNavGraph
import com.jacqulin.gainly.navigation.MainNavGraph
import com.jacqulin.gainly.navigation.RootScreen
//import com.jacqulin.gainly.navigation.RootViewModel
import com.jacqulin.gainly.presentation.ui.theme.GainlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GainlyTheme {
//                val authState by viewModel.authState.collectAsState()
//                RootScreen(authState)
                AuthNavGraph()
//                MainNavGraph()
            }
        }
    }
}
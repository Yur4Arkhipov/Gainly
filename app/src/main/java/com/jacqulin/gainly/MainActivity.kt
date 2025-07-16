package com.jacqulin.gainly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jacqulin.gainly.presentation.ui.theme.GainlyTheme
import com.jacqulin.gainly.signin.SignInScreen
import com.jacqulin.gainly.signup.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GainlyTheme {
//                SignUpScreen()
                SignInScreen()
            }
        }
    }
}
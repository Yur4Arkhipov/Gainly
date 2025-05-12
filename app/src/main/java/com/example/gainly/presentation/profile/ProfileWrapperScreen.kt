package com.example.gainly.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gainly.presentation.auth.AuthRequiredScreen
import com.example.gainly.presentation.auth.AuthState
import com.example.gainly.presentation.auth.viewmodel.AuthViewModel


@Composable
fun ProfileWrapperScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState = authViewModel.isAuthenticated.collectAsState().value

    when (authState) {
        is AuthState.Authenticated -> ProfileScreen()
        is AuthState.Unauthenticated -> AuthRequiredScreen(navController)
    }
}


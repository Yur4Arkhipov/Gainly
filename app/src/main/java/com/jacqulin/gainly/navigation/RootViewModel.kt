package com.jacqulin.gainly.navigation

import androidx.lifecycle.ViewModel
import com.jacqulin.gainly.core.data.auth.AuthManager
import com.jacqulin.gainly.core.util.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    authManager: AuthManager
) : ViewModel() {
    val authState: StateFlow<AuthState> = authManager.authState
}

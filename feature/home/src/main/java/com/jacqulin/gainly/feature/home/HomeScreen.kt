package com.jacqulin.gainly.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jacqulin.gainly.feature.auth.signin.navigation.SignInRoute
import com.jacqulin.gainly.feature.home.navigation.HomeRoute

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home screen",
            modifier = Modifier.clickable(
                onClick = { navController.navigate(SignInRoute) }
            )
        )
    }
}
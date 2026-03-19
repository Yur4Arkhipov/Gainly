package com.jacqulin.gainly.mainapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.component.BottomBarItem
import com.jacqulin.gainly.core.designsystem.component.FloatingBottomBar
import com.jacqulin.gainly.feature.workout.ui.AddWorkoutBottomSheet
import com.jacqulin.gainly.navigation.MainNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val currentDestination = appState.currentDestination
    val currentTopLevel = appState.currentTopLevelDestination
    val bottomBarItems = appState.topLevelDestinations.map { destination ->
        BottomBarItem(
            icon = painterResource(destination.icon),
            contentDescription = destination.iconTextId,
            selected = destination == appState.currentTopLevelDestination,
            onClick = { appState.navigateToTopLevelDestination(destination) }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = currentTopLevel?.titleText ?: "Ошибка") },
                modifier = Modifier,

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current) + 12.dp,
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current) + 12.dp
                )
        ) {
            MainNavHost(appState = appState)

            if (appState.currentTopLevelDestination != null) {
                FloatingBottomBar(
                    items = bottomBarItems,
                    onAddClick =  { appState.showAddWorkoutSheet() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }

    if (appState.showAddWorkoutSheet) {
        AddWorkoutBottomSheet(
            onDismiss = { appState.showAddWorkoutSheet = false }
        )
    }
}
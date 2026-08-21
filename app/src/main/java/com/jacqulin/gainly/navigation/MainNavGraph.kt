package com.jacqulin.gainly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.jacquin.gainly.feature.profile.presentation.navigation.profileSection
import com.jacqulin.gainly.feature.friends.navigation.addFriendsScreen
import com.jacqulin.gainly.feature.friends.navigation.friendsSection
import com.jacqulin.gainly.feature.friends.navigation.friendWorkoutsScreen
import com.jacqulin.gainly.feature.friends.navigation.navigateToAddFriends
import com.jacqulin.gainly.feature.friends.navigation.navigateToFriendWorkouts
import com.jacqulin.gainly.feature.history.presentation.navigation.historySection
import com.jacqulin.gainly.feature.home.navigation.HomeBaseRoute
import com.jacqulin.gainly.feature.home.navigation.homeSection
import com.jacqulin.gainly.mainapp.AppState

@Composable
fun MainNavHost(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeBaseRoute,
        modifier = modifier
    ) {
        homeSection {

        }

        friendsSection(
            onAddFriendsClick = navController::navigateToAddFriends,
            onFriendClick = { friendId, friendName ->
                navController.navigateToFriendWorkouts(friendId, friendName)
            }
        ) {
            addFriendsScreen(
                onBackClick = navController::popBackStack
            )
            friendWorkoutsScreen(
                onBackClick = navController::popBackStack
            )
        }

        historySection {

        }

        profileSection {

        }
    }
}
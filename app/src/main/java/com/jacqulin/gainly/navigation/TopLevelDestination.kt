package com.jacqulin.gainly.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.jacqulin.gainly.core.designsystem.icon.AppIcons
import com.jacqulin.gainly.feature.friends.navigation.FriendsBaseRoute
import com.jacqulin.gainly.feature.friends.navigation.FriendsRoute
import com.jacqulin.gainly.feature.home.navigation.HomeBaseRoute
import com.jacqulin.gainly.feature.home.navigation.HomeRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
//    @StringRes val iconTextId: Int,
    val iconTextId: String,
//    @StringRes val titleTextId: Int,
    val titleTextId: String,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        selectedIcon = AppIcons.HomeSelected,
        unselectedIcon = AppIcons.HomeUnselected,
        iconTextId = "Home",
        titleTextId = "Home",
        route = HomeRoute::class,
        baseRoute = HomeBaseRoute::class
    ),
    FRIENDS(
//        selectedIcon = painterResource(R.drawable.ic_friends_selected),
//        unselectedIcon = painterResource(R.drawable.ic_friends_unselected),
        selectedIcon = AppIcons.FriendsSelected,
        unselectedIcon = AppIcons.FriendsUnselected,
        iconTextId = "Friends",
        titleTextId = "Friends",
        route = FriendsRoute::class,
        baseRoute = FriendsBaseRoute::class
    ),
//    CHALLENGES(
//
//    ),
//    PROFILE(
//
//    )
}
package com.jacqulin.gainly.navigation

import com.jacquin.gainly.feature.profile.presentation.navigation.ProfileBaseRoute
import com.jacquin.gainly.feature.profile.presentation.navigation.ProfileRoute
import com.jacqulin.gainly.core.designsystem.icon.AppIcons
import com.jacqulin.gainly.feature.friends.navigation.FriendsBaseRoute
import com.jacqulin.gainly.feature.friends.navigation.FriendsRoute
import com.jacqulin.gainly.feature.history.navigation.HistoryBaseRoute
import com.jacqulin.gainly.feature.history.navigation.HistoryRoute
import com.jacqulin.gainly.feature.home.navigation.HomeBaseRoute
import com.jacqulin.gainly.feature.home.navigation.HomeRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val icon: Int,
    val iconTextId: String,
//    @StringRes val titleTextId: Int,
    val titleText: String,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        icon = AppIcons.Home,
        iconTextId = "Home",
        titleText = "Home",
        route = HomeRoute::class,
        baseRoute = HomeBaseRoute::class
    ),
    FRIENDS(
        icon = AppIcons.Friends,
        iconTextId = "Friends",
        titleText = "Friends",
        route = FriendsRoute::class,
        baseRoute = FriendsBaseRoute::class
    ),
    HISTORY(
        icon = AppIcons.History,
            iconTextId = "History",
            titleText = "History",
            route = HistoryRoute::class,
            baseRoute = HistoryBaseRoute::class
    ),
    PROFILE(
        icon = AppIcons.Profile,
        iconTextId = "Profile",
        titleText = "Profile",
        route = ProfileRoute::class,
        baseRoute = ProfileBaseRoute::class
    )
}
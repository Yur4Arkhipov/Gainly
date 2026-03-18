package com.jacqulin.gainly.navigation

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
    val titleTextId: String,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        icon = AppIcons.Home,
        iconTextId = "Home",
        titleTextId = "Home",
        route = HomeRoute::class,
        baseRoute = HomeBaseRoute::class
    ),
    FRIENDS(
        icon = AppIcons.Friends,
        iconTextId = "Friends",
        titleTextId = "Friends",
        route = FriendsRoute::class,
        baseRoute = FriendsBaseRoute::class
    ),
    HISTORY(
        icon = AppIcons.History,
            iconTextId = "History",
            titleTextId = "History",
//        route = HistoryRoute::class,
//        baseRoute = HistoryBaseRoute::class
            route = HistoryRoute::class,
            baseRoute = HistoryBaseRoute::class
    ),
    PROFILE(
        icon = AppIcons.Profile,
        iconTextId = "Profile",
        titleTextId = "Profile",
//        route = ProfileRoute::class,
//        baseRoute = ProfileBaseRoute::class
        route = FriendsRoute::class,
        baseRoute = FriendsBaseRoute::class
    )
}
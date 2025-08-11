package com.jacqulin.gainly.navigation

import com.jacqulin.gainly.feature.challenges.navigation.ChallengesRoute
import com.jacqulin.gainly.feature.friends.navigation.FriendsRoute
import com.jacqulin.gainly.feature.home.navigation.HomeRoute
import com.jacqulin.gainly.feature.profile.navigation.ProfileRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val route: KClass<*>,
) {
    FRIENDS(
        route = FriendsRoute::class,
    ),
    CHALLENGES(
        route = ChallengesRoute::class,
    ),
    HOME(
        route = HomeRoute::class,
    ),
    PROFILE(
        route = ProfileRoute::class,
    ),
}
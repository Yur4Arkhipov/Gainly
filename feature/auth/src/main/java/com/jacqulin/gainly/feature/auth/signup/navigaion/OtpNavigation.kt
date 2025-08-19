package com.jacqulin.gainly.feature.auth.signup.navigaion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jacqulin.gainly.feature.auth.signup.otp.OtpScreenHost
import kotlinx.serialization.Serializable

@Serializable
data object OtpRoute

fun NavGraphBuilder.otpNavigation(navController : NavHostController) {
    composable<OtpRoute> {
        OtpScreenHost()
    }
}
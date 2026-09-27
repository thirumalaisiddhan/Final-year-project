package com.callguard.ai.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String = "", val icon: ImageVector? = null) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Home : Screen("home", "Home", Icons.Rounded.Home)
    data object IncomingCall : Screen("incoming_call", "Call Screening", Icons.Rounded.Security)
    data object History : Screen("history", "History", Icons.Rounded.History)
    data object CallDetail : Screen("call_detail/{callId}", "Call Detail") {
        fun createRoute(callId: String) = "call_detail/$callId"
    }
    data object TrustedCallers : Screen("trusted_callers", "Trusted", Icons.Rounded.VerifiedUser)
    data object AddTrusted : Screen("add_trusted", "Add Trusted Caller")
    data object ServiceContext : Screen("service_context", "Services")
    data object Analytics : Screen("analytics", "Analytics", Icons.Rounded.Analytics)
    data object Settings : Screen("settings", "Settings", Icons.Rounded.Settings)
    data object About : Screen("about", "About Project")
}

// Evaluated safely when invoked, preventing any static companion object circular initialization
val bottomNavScreens: List<Screen>
    get() = listOf(
        Screen.Home,
        Screen.History,
        Screen.TrustedCallers,
        Screen.Analytics,
        Screen.Settings
    )

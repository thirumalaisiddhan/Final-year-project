package com.callguard.ai.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.callguard.ai.CallGuardApplication
import com.callguard.ai.ui.screens.about.AboutScreen
import com.callguard.ai.ui.screens.analytics.AnalyticsScreen
import com.callguard.ai.ui.screens.analytics.AnalyticsViewModel
import com.callguard.ai.ui.screens.context.ContextViewModel
import com.callguard.ai.ui.screens.context.ServiceContextScreen
import com.callguard.ai.ui.screens.detail.CallDetailScreen
import com.callguard.ai.ui.screens.history.CallHistoryScreen
import com.callguard.ai.ui.screens.history.HistoryViewModel
import com.callguard.ai.ui.screens.home.HomeScreen
import com.callguard.ai.ui.screens.home.HomeViewModel
import com.callguard.ai.ui.screens.incoming.IncomingCallScreen
import com.callguard.ai.ui.screens.incoming.IncomingCallViewModel
import com.callguard.ai.ui.screens.onboarding.OnboardingScreen
import com.callguard.ai.ui.screens.settings.SettingsScreen
import com.callguard.ai.ui.screens.settings.SettingsViewModel
import com.callguard.ai.ui.screens.splash.SplashScreen
import com.callguard.ai.ui.screens.trusted.AddTrustedCallerScreen
import com.callguard.ai.ui.screens.trusted.TrustedCallersScreen
import com.callguard.ai.ui.screens.trusted.TrustedViewModel

@Composable
fun CallGuardAppRoot(
    navController: NavHostController = rememberNavController()
) {
    val app = CallGuardApplication.instance
    val repository = app.repository
    val predictionService = app.predictionService
    val decisionEngine = app.decisionEngine

    // Root-scoped ViewModels retained securely across app lifetime
    val homeViewModel = remember { HomeViewModel(repository) }
    val historyViewModel = remember { HistoryViewModel(repository) }
    val incomingViewModel = remember { IncomingCallViewModel(repository, predictionService, decisionEngine) }
    val trustedViewModel = remember { TrustedViewModel(repository) }
    val contextViewModel = remember { ContextViewModel(repository) }
    val analyticsViewModel = remember { AnalyticsViewModel(repository) }
    val settingsViewModel = remember { SettingsViewModel(repository) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val shouldShowBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.History.route,
        Screen.TrustedCallers.route,
        Screen.Analytics.route,
        Screen.Settings.route
    )

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                CallGuardBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onNavigateNext = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onFinished = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onNavigateToHistory = { navController.navigate(Screen.History.route) },
                    onNavigateToTrusted = { navController.navigate(Screen.TrustedCallers.route) },
                    onNavigateToContext = { navController.navigate(Screen.ServiceContext.route) },
                    onNavigateToAnalytics = { navController.navigate(Screen.Analytics.route) },
                    onNavigateToIncomingCall = { navController.navigate(Screen.IncomingCall.route) },
                    onNavigateToCallDetail = { callId ->
                        navController.navigate(Screen.CallDetail.createRoute(callId))
                    }
                )
            }

            composable(Screen.IncomingCall.route) {
                IncomingCallScreen(
                    viewModel = incomingViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.History.route) {
                CallHistoryScreen(
                    viewModel = historyViewModel,
                    onNavigateToCallDetail = { callId ->
                        navController.navigate(Screen.CallDetail.createRoute(callId))
                    }
                )
            }

            composable(
                route = Screen.CallDetail.route,
                arguments = listOf(navArgument("callId") { type = NavType.StringType })
            ) { backStackEntry ->
                val callId = backStackEntry.arguments?.getString("callId") ?: ""
                CallDetailScreen(
                    callId = callId,
                    repository = repository,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.TrustedCallers.route) {
                TrustedCallersScreen(
                    viewModel = trustedViewModel,
                    onNavigateToAddTrusted = { navController.navigate(Screen.AddTrusted.route) }
                )
            }

            composable(Screen.AddTrusted.route) {
                AddTrustedCallerScreen(
                    viewModel = trustedViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.ServiceContext.route) {
                ServiceContextScreen(
                    viewModel = contextViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Analytics.route) {
                AnalyticsScreen(
                    viewModel = analyticsViewModel
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onNavigateToAbout = { navController.navigate(Screen.About.route) }
                )
            }

            composable(Screen.About.route) {
                AboutScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

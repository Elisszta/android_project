package com.everyclocked.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.everyclocked.focusmode.FocusMode
import com.everyclocked.home.MainPage
import com.everyclocked.settings.SettingPage

@Composable
fun ClockNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = EveryClockedDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(EveryClockedDestinations.HOME_ROUTE) {
            MainPage()
        }
        composable(EveryClockedDestinations.FOCUS_MODE_ROUTE) {
            FocusMode()
        }
        composable(EveryClockedDestinations.SETTINGS_ROUTE) {
            SettingPage()
        }
    }
}
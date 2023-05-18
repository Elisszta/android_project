package com.everyclocked

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.everyclocked.home.MainPage
import com.everyclocked.parts.AppDrawer
import com.everyclocked.ui.theme.EveryClockedTheme
import com.everyclocked.utils.ClockNavGraph
import com.everyclocked.utils.EveryClockedDestinations
import com.everyclocked.utils.EveryClockedNavigationActions
import kotlinx.coroutines.launch

@Composable
fun EveryClockApp(
    windowSizeClass: WindowSizeClass,
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        EveryClockedNavigationActions(navController)
    }

    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: EveryClockedDestinations.HOME_ROUTE
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToHome = navigationActions.navigateToHome,
                navigateToSettings = navigationActions.navigateToSettings,
                openFocusMode = navigationActions.navigateToFocusMode,
                closeDrawer = { coroutineScope.launch { drawerState.close() } })
        }) {
        ClockNavGraph(
            navController = navController,
            openDrawer = { coroutineScope.launch { drawerState.open() } }
        )
    }
}
package com.everyclocked.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [EveryClocked].
 */

object EveryClockedDestinations {
    const val HOME_ROUTE = "home"
    const val FOCUS_MODE_ROUTE = "focus_mode"
    const val SETTINGS_ROUTE = "settings"
}

/**
 * Models the navigation actions in the app.
 */

class EveryClockedNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(EveryClockedDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToFocusMode: () -> Unit = {
        navController.navigate(EveryClockedDestinations.FOCUS_MODE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(EveryClockedDestinations.SETTINGS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
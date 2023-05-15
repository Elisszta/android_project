package com.everyclocked.parts

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Man2
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.everyclocked.R
import com.everyclocked.ui.theme.EveryClockedTheme
import com.everyclocked.utils.EveryClockedDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    openFocusMode: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet {
        EveryClockedLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
        )
        NavigationDrawerItem(
            label = { Text(text = "Home")},
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            selected = currentRoute == EveryClockedDestinations.HOME_ROUTE,
            onClick = { navigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Focus Mode")},
            icon = { Icon(Icons.Filled.Man2, contentDescription = null) },
            selected = currentRoute == EveryClockedDestinations.FOCUS_MODE_ROUTE,
            onClick = { openFocusMode(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Settings")},
            icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
            selected = currentRoute == EveryClockedDestinations.SETTINGS_ROUTE,
            onClick = { navigateToSettings(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun EveryClockedLogo(modifier: Modifier = Modifier) {
    Row(modifier) {
        Icon(
            painter = painterResource(R.drawable.ic_clock_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(12.dp))
        Text(text = "EveryClocked", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun DeveloperLogo(modifier: Modifier = Modifier) {
    Column(modifier) {
        Icon(
            painter = painterResource(R.drawable.ic_clock_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(12.dp))
        Text(text = "Developer", style = MaterialTheme.typography.titleMedium)
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    EveryClockedTheme {
        AppDrawer(
            currentRoute = "Home",
            navigateToHome = { },
            navigateToSettings = { },
            openFocusMode = { },
            closeDrawer = { }
        )
    }
}

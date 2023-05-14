package com.android_project.menu

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android_project.R
import com.android_project.ui.theme.EveryClockedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToInterests: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet {
        EveryClockedLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
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
        Spacer(Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.ic_clock_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
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
            navigateToInterests = { },
            closeDrawer = { }
        )
    }
}

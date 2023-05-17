package com.everyclocked.parts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.everyclocked.ui.theme.EveryClockedTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EveryClockedTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    NavigationDrawer {
        NavContent(selectedIndex = it)
    }

}

val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun NavigationDrawer(
    content: @Composable (Int) -> Unit
) {
    val windowWidthSizeClass = WindowWidthSizeClass.Compact
        //calculateWindowSizeClass(LocalContext.current as ComponentActivity).widthSizeClass

    val selectedState = rememberSaveable { mutableStateOf(0) }

    if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
            DrawerContent(
                drawerState = drawerState,
                selectedState = selectedState,
                windowWidthSizeClass = windowWidthSizeClass
            )
        }) { content(selectedState.value) }
    } else {
        PermanentNavigationDrawer(drawerContent = {
            DrawerContent(
                selectedState = selectedState,
                windowWidthSizeClass = windowWidthSizeClass
            )
        }) { content(selectedState.value) }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    drawerState: DrawerState? = null,
    selectedState: MutableState<Int>,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    val scope = rememberCoroutineScope()
    val isMedium = windowWidthSizeClass == WindowWidthSizeClass.Medium
    val isCompat = windowWidthSizeClass == WindowWidthSizeClass.Compact

    val sheetWidth = when{
        isCompat -> 300.dp
        isMedium -> 100.dp
        else -> 220.dp
    }

    ModalDrawerSheet(
        drawerShape = if (isCompat) CutCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
        else  RectangleShape,
        modifier = Modifier.width(sheetWidth)
    ) {
        items.forEachIndexed { index, item ->
            NavigationDrawerItem(
                icon = { Icon(item, contentDescription = null) },
                label = {
                    if (isMedium){ }  else Text(item.name)
                },
                selected = index == selectedState.value,
                onClick = {
                    scope.launch { drawerState?.close() }
                    selectedState.value = index
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavContent(selectedIndex: Int) {
    Scaffold() {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color.Cyan)) {
            Text(text = "$selectedIndex", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerPreview() {
    App()
}
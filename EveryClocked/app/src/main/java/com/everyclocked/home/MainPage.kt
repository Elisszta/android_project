package com.everyclocked.home

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Resources.Theme
import android.graphics.drawable.DrawableContainer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.everyclocked.R
import com.everyclocked.parts.AppDrawer
import com.everyclocked.ui.theme.EveryClockedTheme
import com.everyclocked.utils.ClockNavGraph
import com.everyclocked.utils.EveryClockedDestinations
import com.everyclocked.utils.EveryClockedNavigationActions
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    BoxWithConstraints() {
        // Adopt as more devices as possible
        val windowWidth = maxWidth
        val buttonSize = windowWidth * 3 / 5
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("EveryClocked")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clock_logo),
                                contentDescription = null
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(windowWidth)
                        .width(windowWidth)
                        .padding(top = 70.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(buttonSize)
                            .clip(CircleShape),
                    ) {
                        Text("size")
                    }
                    Canvas(modifier = Modifier
                        .fillMaxSize()
                        .size(250.dp)) {
                        val strokeWidth = 4.dp.toPx()
                        val innerRadius = (size.minDimension - strokeWidth) / 2
                        drawArc(
                            color = Color.LightGray,
                            startAngle = -90f,
                            sweepAngle = 270f, // set progress here
                            useCenter = false,
                            topLeft = Offset(
                                (size.width - innerRadius * 2) / 2,
                                (size.height - innerRadius * 2) / 2
                            ),
                            size = Size(innerRadius * 2, innerRadius * 2),
                            style = Stroke(width = strokeWidth)
                        )
                    }
//                Text("Button", color = Color.White)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "time state",
                    fontSize = 32.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn(
                    modifier = Modifier
                        .weight(45f),
                ) {
                    items(5) { index ->
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .height(80.dp),
                            shape = RoundedCornerShape(15.dp),
//                        shape = CircleShape,
                            colors = ButtonDefaults.buttonColors
                                (containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Row() {
                                Text(
                                    text = "Mission $index",
                                    modifier = Modifier
                                        .weight(1f),
                                    textAlign = TextAlign.Start
                                )
                                Text(
                                    text = "Mission ETF",
                                    modifier = Modifier
                                        .weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}


/**
 * Confirm the drawer state to pass to the modal drawer.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    return if (!isExpandedScreen) {
        drawerState
    } else {
        DrawerState(DrawerValue.Closed)
    }
}

@Preview
@Composable
fun PreviewMainPageUI() {
    EveryClockedTheme() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MainPage()
        }
    }
}

//Dark Version Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainPageUIDark() {
    EveryClockedTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MainPage()
        }
    }
}

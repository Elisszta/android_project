package com.everyclocked.home

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.everyclocked.R
import com.everyclocked.ui.theme.EveryClockedTheme
import com.everyclocked.utilclass.Mission
import com.everyclocked.utilclass.SingleMissionLayout
import java.time.Duration

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    isFocusModeOn: MutableState<Boolean> = mutableStateOf(false),
    clockColor: Color = MaterialTheme.colorScheme.primary,
    ) {
    BoxWithConstraints {
        // Adopt as more devices as possible
        val windowWidth = maxWidth
        val buttonSize = windowWidth * 3 / 5
        val missionList = remember {
            mutableStateListOf<Mission>()
        }
        val displayMsg = remember {
            mutableStateOf("Clock up")
        }
        val curMission = remember {
            mutableStateOf<Mission?>(null)
        }
        val missionRemoved = remember {
            mutableStateOf(false)
        }
        val newMissionName = remember {
            mutableStateOf("New Mission")
        }
        val newMissionDuration = remember {
            mutableStateOf("25")
        }
        val newMissionCreate = remember {
            mutableStateOf(false)
        }
        if (newMissionCreate.value) {
            NewMissionDialog(newMissionName, newMissionDuration, newMissionCreate, missionList)
        }
        if (curMission.value != null) {
            if (!curMission.value!!.isHidden) {
                displayMsg.value = curMission.value!!.missionName
            }
        }
        if (missionRemoved.value) {
            missionRemoved.value = false
            changeDisplay(display = displayMsg, curMission = curMission)
        }
        LaunchedEffect(key1 = missionRemoved) {
            missionList.removeIf{it.isHidden}
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("EveryClocked")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { openDrawer }
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
                FloatingActionButton(onClick = {
                    newMissionCreate.value = true

                }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
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
                        Text(displayMsg.value)
                    }
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .size(250.dp)
                    ) {
                        val strokeWidth = 4.dp.toPx()
                        val innerRadius = (size.minDimension - strokeWidth) / 2
                        drawArc(
                            color = Color.LightGray,
                            startAngle = -90f,
                            sweepAngle = 350f, // set progress here
                            useCenter = false,
                            topLeft = Offset(
                                (size.width - innerRadius * 2) / 2,
                                (size.height - innerRadius * 2) / 2
                            ),
                            size = Size(innerRadius * 2, innerRadius * 2),
                            style = Stroke(width = strokeWidth)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "TimeState",
                    fontSize = 32.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn(
                    modifier = Modifier
                        .weight(45f),
                ) {
                    itemsIndexed(missionList) { index, item ->
                        if (!missionList[index].isHidden) {
                            SingleMissionLayout(
                                missionList,
                                index,
                                curMission = curMission,
                                windowWidth,
                                missionRemoved
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainPageUI() {
    EveryClockedTheme() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MainPage(
                openDrawer = { }
            )
        }
    }
}

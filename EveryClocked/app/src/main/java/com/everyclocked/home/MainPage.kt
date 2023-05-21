package com.everyclocked.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.everyclocked.R
import com.everyclocked.utilclass.Mission
import com.everyclocked.utils.ClockViewModel
import kotlin.properties.Delegates


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    clockVM: ClockViewModel,
    openDrawer: () -> Unit = {},
) {
    BoxWithConstraints {

        val nowContext = LocalContext.current

        // Adopt as more devices as possible
        val windowWidth = maxWidth
        val buttonSize = windowWidth * 3 / 5

        // Custom color vars
        val hasCc = clockVM.hasCustomColor.observeAsState()
        val ccRed = clockVM.clockColorR.observeAsState()
        val ccBlue = clockVM.clockColorB.observeAsState()
        val ccGreen = clockVM.clockColorG.observeAsState()

        var clockColor = MaterialTheme.colorScheme.secondary
        var clockColorReversed = Color(clockColor.toArgb() xor 0xffffff)

        if (hasCc.value!!) {
            clockColor = Color(ccRed.value!!, ccGreen.value!!, ccBlue.value!!)
            clockColorReversed = Color(clockColor.toArgb() xor 0xffffff)
        }

        // Application val
        val missionList = remember {
            mutableStateListOf<Mission>()
        }
        val isReadComplete = remember {
            mutableStateOf(false)
        }
        // Read in mission list when application is launched
        LaunchedEffect(key1 = false) {
            clockVM.readMissionList(missionList)
            isReadComplete.value = true
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
        val newMission = remember {
            mutableStateOf<Mission?>(null)
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
        val isTimerStart = remember {
            mutableStateOf(false)
        }

        // Set mission working now
        LaunchedEffect(key1 = missionList.size){
            if (isReadComplete.value &&
                clockVM.getNowMission() != null &&
                clockVM.getNowMission()!! > 0 && clockVM.getNowMission()!! <= missionList.size
            ) {
                curMission.value = missionList[clockVM.getNowMission()!! - 1]
            }
        }

        // some side effect
        if (newMissionCreate.value) {
            NewMissionDialog(
                newMissionName,
                newMissionDuration,
                newMissionCreate,
                newMission
            )
        }
        if (newMission.value != null) {
            missionList.add(newMission.value!!)
            clockVM.addNewMission(missionList.size, newMission.value!!)
            newMission.value = null
        }
        if (curMission.value != null) {
            if (!curMission.value!!.isHidden) {
                displayMsg.value = curMission.value!!.missionName
            }
        }
        if (missionRemoved.value) {
            clockVM.setNowMission(-1)
            missionRemoved.value = false
            changeDisplay(display = displayMsg, curMission = curMission)
            clockVM.reWriteList(missionList)
        }
        LaunchedEffect(missionRemoved.value) {
            missionList.removeIf { it.isHidden }
        }
        if (curMission.value != null && curMission.value!!.isHidden){
            curMission.value = null
        }

        // Timer Function
        var hours by Delegates.notNull<Int>()
        var minutes by Delegates.notNull<Int>()
        var seconds by Delegates.notNull<Int>()
        if (isTimerStart.value) {
            var trigger by remember { mutableStateOf(curMission.value!!.remainingTime) }
            val elapsed by animateIntAsState(
                targetValue = trigger * 1000,
                animationSpec =
                tween(curMission.value!!.remainingTime * 1000, easing = LinearEasing)
            )

            DisposableEffect(Unit) {
                trigger = 0
                onDispose { }
            }
            val (hou, min, sec) = remember(elapsed / 1000) {
                val elapsedInSec = elapsed / 1000
                val hou = elapsedInSec / 3600
                val min = elapsedInSec / 60 - hou * 60
                val sec = elapsedInSec % 60
                Triple(hou, min, sec)
            }
            hours = hou
            minutes = min
            seconds = sec
            curMission.value!!.remainingTime = hours * 3600 + minutes * 60 + seconds
        }
        if (!isTimerStart.value && isReadComplete.value) {
            clockVM.reWriteList(missionList)
        }

        // Main Body of the Layout
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("EveryClocked")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { openDrawer() }
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
                        .padding(it)
                ) {
                    Button(
                        onClick = {
                            if (curMission.value == null) {
                                Toast.makeText(
                                    nowContext, "Please select a mission",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                isTimerStart.value = !isTimerStart.value
                            }
                        },
                        modifier = Modifier
                            .size(buttonSize)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors
                            (containerColor = clockColor)
                    ) {
                        Text(
                            text = displayMsg.value,
                            color = clockColorReversed,
                            style = MaterialTheme.typography.titleLarge
                        )
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
                            sweepAngle = if (isTimerStart.value && curMission.value != null) {
                                curMission.value!!.remainingTime /
                                        curMission.value!!.totalTime.toFloat() * 360f
                            } else if (
                                curMission.value != null &&
                                curMission.value!!.remainingTime != curMission.value!!.totalTime
                            ) {
                                curMission.value!!.remainingTime /
                                        curMission.value!!.totalTime.toFloat() * 360f
                            } else {
                                360f
                            }, // When pause, remember the progress status
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
                    text = if (isTimerStart.value) {
                        val formattedHrs = String.format("%02d", hours)
                        val formattedMnt = String.format("%02d", minutes)
                        val formattedSec = String.format("%02d", seconds)
                        if (hours > 0) {
                            "$formattedHrs:$formattedMnt:$formattedSec"
                        } else if (curMission.value!!.remainingTime > 0) {
                            "$formattedMnt:$formattedSec"
                        } else {
                            "Time is up."
                        }
                    } //State when timer is running
                    else if (curMission.value != null) {
                        val formattedHrs =
                            String.format("%02d", curMission.value!!.remainingTime / 3600)
                        val formattedMnt =
                            String.format(
                                "%02d", curMission.value!!.remainingTime / 60 -
                                        curMission.value!!.remainingTime / 3600 * 60
                            )
                        val formattedSec =
                            String.format("%02d", curMission.value!!.remainingTime % 60)
                        if (curMission.value!!.remainingTime / 3600 > 0) {
                            "$formattedHrs:$formattedMnt:$formattedSec"
                        } else if (curMission.value!!.remainingTime > 0) {
                            "$formattedMnt:$formattedSec"
                        } else {
                            "Time is up."
                        }
                    } //State when timer is paused
                    else {
                        "Time State"
                    },
                    fontSize = 32.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn(
                    modifier = Modifier
                        .weight(45f),
                ) {
                    itemsIndexed(missionList) { index, _ ->
                        if (!missionList[index].isHidden) {
                            SingleMissionLayout(
                                missionList = missionList,
                                missionIndex = index,
                                curMission = curMission,
                                width = windowWidth,
                                curMissionChanged = missionRemoved,
                                viewModel = clockVM,
                                isTimerRunning = isTimerStart
                            )
                        }
                    }
                }
            }
        }
    }
}
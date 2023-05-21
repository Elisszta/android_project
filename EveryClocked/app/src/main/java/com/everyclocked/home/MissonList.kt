package com.everyclocked.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.everyclocked.utilclass.Mission
import com.everyclocked.utils.ClockViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

@Composable
fun SingleMissionLayout(
    missionList: SnapshotStateList<Mission>, missionIndex: Int,
    curMission: MutableState<Mission?>, width: Dp,
    curMissionChanged: MutableState<Boolean>,
    viewModel: ClockViewModel, isTimerRunning: MutableState<Boolean>
) {
    val context = LocalContext.current
    val mission = missionList[missionIndex]
    val offsetXs = remember {
        missionList.map { Animatable(0f) }
    }
    val widthPx = with(LocalDensity.current) {
        width.toPx()
    }
    val visible = remember {
        missionList.map { mutableStateOf(true) }
    }
    val name = mission.missionName
    val remainingTime = mission.remainingTime
    val minutes = remainingTime / 60
    val seconds = remainingTime - minutes * 60
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(visible = visible[missionIndex].value) {
        Button(
            onClick = {
                viewModel.setNowMission(missionIndex + 1)
                curMission.value = missionList[viewModel.getNowMission()!! - 1]
            },
            modifier = Modifier
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            offsetXs[missionIndex].snapTo(offsetXs[missionIndex].value + delta)
                        }
                    },
                    onDragStopped = {
                        if (-offsetXs[missionIndex].value > (widthPx / 4) && !isTimerRunning.value) {
                            coroutineScope.launch {
                                offsetXs[missionIndex].animateTo(
                                    targetValue = -widthPx,
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        delayMillis = 0
                                    ),
                                )
                                Thread.sleep(10)
                                visible[missionIndex].value = false
                                mission.isHidden = true
                                curMissionChanged.value = true
                            }
                        } else {
                            coroutineScope.launch {
                                offsetXs[missionIndex].animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        delayMillis = 0
                                    )
                                )
                            }
                            if (isTimerRunning.value) {
                                Toast.makeText(context, "You are not allowed to remove " +
                                        "mission when timer is running.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                )
                .offset { IntOffset(offsetXs[missionIndex].value.roundToInt(), 0) }
                .padding(8.dp)
                .height(80.dp)
                .width(width),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors
                (containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Row() {
                Text(
                    text = name,
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "$minutes:" + "%02d".format(seconds),
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
package com.everyclocked.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import com.everyclocked.utilclass.Mission

@Composable
fun SingleMissionLayout(missionList: MutableList<Mission>, missionIndex: Int,
                        curMission: MutableState<Mission?>, width: Dp,
                        curMissionChanged: MutableState<Boolean>) {
    val mission = missionList[missionIndex]
    val offsetXs = remember {
        missionList.map{ Animatable(0f) }
    }
    val widthPx = with(LocalDensity.current) {
        width.toPx()
    }
    val visible = remember {
        missionList.map { mutableStateOf(true) }
    }
    val name = mission.missionName
    val remainingTime = mission.totalTime
    val minutes = remainingTime / 60
    val seconds = remainingTime - minutes * 60
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(visible = visible[missionIndex].value) {
        Button(
            onClick = { curMission.value = mission },
            modifier = Modifier
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            offsetXs[missionIndex].
                            snapTo(offsetXs[missionIndex].value + delta)
                        }
                    },
                    onDragStopped = {
                        if (-offsetXs[missionIndex].value > (widthPx / 4)) {
                            coroutineScope.launch {
                                offsetXs[missionIndex].animateTo(
                                    targetValue = -widthPx,
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        delayMillis = 0
                                    ),
                                )
                                visible[missionIndex].value = false
                            }
                            mission.isHidden = true
                            curMissionChanged.value = true
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
                    text = name + "${offsetXs[missionIndex].value}" + "   ${visible[missionIndex].value}",
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
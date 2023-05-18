package com.everyclocked.utilclass

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun MissionLayout(missionList: MutableList<Mission>, missionIndex: Int,
                  curMission: MutableState<Mission?>, width: Dp) {
    var mission = missionList[missionIndex]
    var offsetXs = remember {
        missionList.map{ mutableStateOf(0f) }
    }
    val widthPx = with(LocalDensity.current) {
        width.toPx()
    }
    val visible = remember {
        missionList.map { mutableStateOf(true) }
    }
    val name = mission.missionName
    val remainingTime = mission.remainingTime
    val minutes = remainingTime.toMinutes()
    val seconds = remainingTime.seconds - minutes * 60
    AnimatedVisibility(visible = visible[missionIndex].value) {
        Button(
            onClick = { curMission.value = mission },
            modifier = Modifier
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetXs[missionIndex].value += delta
                    },
                    onDragStopped = {
                        if (offsetXs[missionIndex].value > (widthPx / 4)) {
                            visible[missionIndex].value = false
                            mission.isHidden = true
                        } else {
                            offsetXs[missionIndex].value = 0f
                        }
                    }
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
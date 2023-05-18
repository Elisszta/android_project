package com.everyclocked.utilclass

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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun MissionLayout(missionList: MutableList<Mission>, mission: Mission,
                  curMission: MutableState<Mission?>, width: Dp) {
    var offsetX = remember {
        mutableStateOf(0f)
    }
    val widthPx = with(LocalDensity.current) {
        width.toPx()
    }
    val name = mission.missionName
    val remainingTime = mission.remainingTime
    val minutes = remainingTime.toMinutes()
    val seconds = remainingTime.seconds - minutes * 60
    Button(
        onClick = { curMission.value = mission },
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX.value += delta
                },
                onDragStopped = {
                    if (offsetX.value > (widthPx / 4)) {
                        missionList.remove(mission)
                    } else {
                        offsetX.value = 0f
                    }
                }
            )
            .padding(8.dp)
            .height(80.dp)
            .width(width),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors
            (containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row() {
            Text(
                text = name + "${offsetX.value}" + " $widthPx",
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
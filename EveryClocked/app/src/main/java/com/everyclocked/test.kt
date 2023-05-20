package com.everyclocked

import android.animation.ValueAnimator
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview


data class Mission(
    var missionName: String = "mission",
    var remainingTime: Int = 25 * 60,
    var isPaused: Boolean = false,
    var isHidden: Boolean = false,
)

@Composable
fun CountdownTimer(mission: Mission) {
    val remainingTime = remember { mutableStateOf(mission.remainingTime) }
    val isPaused = remember { mutableStateOf(mission.isPaused) }
    val isHidden = remember { mutableStateOf(mission.isHidden) }

    val valueAnimator = remember { ValueAnimator.ofInt(remainingTime.value, 0) }
    valueAnimator.duration = remainingTime.value.toLong() * 1000
    valueAnimator.addUpdateListener {
        remainingTime.value = it.animatedValue as Int
    }

    if (!isPaused.value && !isHidden.value) {
        valueAnimator.start()
    } else {
        valueAnimator.pause()
    }

    val totalSeconds = mission.remainingTime
    val remainingSeconds = remainingTime.value
    val progress = (totalSeconds - remainingSeconds).toFloat() / totalSeconds.toFloat()

    Column {
        Text(text = "Remaining time: $remainingSeconds")
        LinearProgressIndicator(progress = progress)
        Button(onClick = {
            isPaused.value = !isPaused.value
            isHidden.value = false
        }) {
            Text(if (isPaused.value) "Resume" else "Pause")
        }
        Button(onClick = {
            remainingTime.value = mission.remainingTime
            isPaused.value = false
            isHidden.value = false
        }) {
            Text("Restart")
        }
        Button(onClick = {
            isHidden.value = !isHidden.value
        }) {
            Text(if (isHidden.value) "Show" else "Hide")
        }
    }
}

@Preview
@Composable
fun CountdownTimerPreview() {
    CountdownTimer(Mission())
}

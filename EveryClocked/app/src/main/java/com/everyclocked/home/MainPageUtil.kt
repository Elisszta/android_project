package com.everyclocked.home

import androidx.compose.runtime.MutableState
import com.everyclocked.utilclass.Mission

fun changeDisplay(display: MutableState<String>, curMission: MutableState<Mission?>) {
    if (curMission.value != null) {
        if (!curMission.value!!.isHidden) {
            display.value = curMission.value!!.missionName
        } else {
            display.value = "Clock up"
        }
    }
}
package com.everyclocked.utilclass

import java.time.Duration

data class Mission(
    var missionName: String = "mission",
    var remainingTime: Int = 25 * 60,
    var isHidden: Boolean = false,
) {
}
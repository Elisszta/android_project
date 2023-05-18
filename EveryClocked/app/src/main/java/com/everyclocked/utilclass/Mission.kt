package com.everyclocked.utilclass

import java.time.Duration

data class Mission(
    var missionName: String = "mission",
    var remainingTime: Duration = Duration.ofMinutes(25),
    var isHidden: Boolean = false,
) {
}
package com.everyclocked.utilclass

import java.time.Duration

class Mission(
    var missionName: String = "mission",
    var remainingTime: Duration = Duration.ofMinutes(25)
) {
}
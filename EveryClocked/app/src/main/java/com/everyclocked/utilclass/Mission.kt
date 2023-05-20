package com.everyclocked.utilclass

data class Mission(
    var missionName: String = "mission",
    var totalTime: Int = 25 * 60,
    var remainingTime: Int = 25 * 60,
    var isHidden: Boolean = false,
) {
}
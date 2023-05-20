package com.everyclocked.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everyclocked.utilclass.Mission
import com.google.gson.Gson

class ClockViewModel(application: Application): AndroidViewModel(application) {
    private val sharedPreference =
        application.getSharedPreferences("EveryClocked", Context.MODE_PRIVATE)
    private val _isFocusMode = MutableLiveData<Boolean>()
    val isFocusMode: LiveData<Boolean> = _isFocusMode
    private val _hasCustomColor = MutableLiveData<Boolean>()
    val hasCustomColor: LiveData<Boolean> = _hasCustomColor
    private val _clockColorR = MutableLiveData<Float>(0f)
    val clockColorR: LiveData<Float> = _clockColorR
    private val _clockColorG = MutableLiveData<Float>(0f)
    val clockColorG: LiveData<Float> = _clockColorG
    private val _clockColorB= MutableLiveData<Float>(0f)
    val clockColorB: LiveData<Float> = _clockColorB

    init {
        if (!sharedPreference.getBoolean("focusE", false)) {
            sharedPreference.edit().putBoolean("focusE", true).apply()
            sharedPreference.edit().putBoolean("focusM", false).apply()
        }
        _hasCustomColor.value = sharedPreference.getBoolean("colorE", false)
        _clockColorR.value = sharedPreference.getFloat("R", 0f)
        _clockColorB.value = sharedPreference.getFloat("B", 0f)
        _clockColorG.value = sharedPreference.getFloat("G", 0f)
        if (!sharedPreference.contains("num")) {
            sharedPreference.edit().putInt("num", 0).apply()
        }

    }

    /** This function sets the color for the clock on the Main page */
    fun setRGB(R: Float, G: Float, B: Float) {
        sharedPreference.edit().putBoolean("colorE", true).apply()
        sharedPreference.edit().putFloat("R", R).putFloat("G", G).putFloat("B", B).apply()
        _clockColorR.value = R
        _clockColorG.value = G
        _clockColorB.value = B
    }

    fun rmCustomColor() {
        sharedPreference.edit().putBoolean("colorE", false).apply()
    }

    fun hasCustomColor(): Boolean {
        return sharedPreference.getBoolean("colorE", false)
    }

    /** This function reads the mission list */
    fun readMissionList(missionList: SnapshotStateList<Mission>): SnapshotStateList<Mission> {
        val numMission = sharedPreference.getInt("num", 0)
        var index = 1
        val gson = Gson()
        while (index <= numMission) {
            val savedJson = sharedPreference.getString("m${index}", null)
            if (savedJson != null) {
                missionList.add(gson.fromJson(savedJson, Mission::class.java))
            }
            index += 1
        }
        return missionList
    }

    /** adds new mission to the list */
    fun addNewMission(index: Int, mission: Mission) {
        val gson = Gson()
        sharedPreference.edit().putString("m${index}", gson.toJson(mission))
            .putInt("num", sharedPreference.getInt("num", -1) + 1).apply()
    }

    /** This function updates the list */
    fun reWriteList(list: SnapshotStateList<Mission>) {
        val gson = Gson()
        val newSize = list.size
        sharedPreference.edit().putInt("num", newSize).apply()
        var index = 1
        while (index <= newSize) {
            sharedPreference.edit().putString("m${index}", gson.toJson(list[index - 1])).apply()
            index += 1
        }
    }
}
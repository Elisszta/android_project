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
    private val _clockColorR = MutableLiveData<Float>()
    val clockColorR: LiveData<Float> = _clockColorR
    private val _clockColorG = MutableLiveData<Float>()
    val clockColorG: LiveData<Float> = _clockColorG
    private val _clockColorB= MutableLiveData<Float>()
    val clockColorB: LiveData<Float> = _clockColorB

    init {
        if (!sharedPreference.getBoolean("focusE", false)) {
            sharedPreference.edit().putBoolean("focusE", true).apply()
            sharedPreference.edit().putBoolean("focusM", false).apply()
        }
        if (sharedPreference.getBoolean("colorE", false)) {
            _clockColorR.value = sharedPreference.getFloat("R", 0f)
            _clockColorG.value = sharedPreference.getFloat("G", 0f)
            _clockColorB.value = sharedPreference.getFloat("B", 0f)
        }
        if (sharedPreference.getInt("num", -1) < 0) {
            sharedPreference.edit().putInt("num", 0).apply()
        }

        /* Test */
//        val gson = Gson()
//        val mission = Mission("Saved Mission")
//        sharedPreference.edit().putString("test", gson.toJson(mission)).apply()
    }

    /** Test function */
    fun getOne(): Mission {
        val saved = sharedPreference.getString("test", null)
        if (saved != null) {
            val gson = Gson()
            return gson.fromJson(saved, Mission::class.java)
        }
        return Mission("Wrong one!")
    }

    fun insertOne(list: SnapshotStateList<Mission>) {
        val gson = Gson()
        val saved = sharedPreference.getString("test", null)
        list.add(gson.fromJson(saved, Mission::class.java))
    }

    /** Test function end */

    fun getSize(): Int {
        return sharedPreference.getInt("num", -1)
    }

    /** This function sets the color for the clock on the Main page */
    fun setRGB(R: Float, G: Float, B: Float) {
        sharedPreference.edit().putBoolean("colorE", true).apply()
        sharedPreference.edit().putFloat("R", R).putFloat("G", G).putFloat("B", B).apply()
    }

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

    fun addNewMission(index: Int, mission: Mission) {
        val gson = Gson()
        sharedPreference.edit().putString("m${index}", gson.toJson(mission))
            .putInt("num", sharedPreference.getInt("num", -1) + 1).apply()
    }
}
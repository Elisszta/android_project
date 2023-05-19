package com.everyclocked.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.everyclocked.utilclass.Mission
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import java.time.Duration


fun changeDisplay(display: MutableState<String>, curMission: MutableState<Mission?>) {
    if (curMission.value != null) {
        if (!curMission.value!!.isHidden) {
            display.value = curMission.value!!.missionName
        } else {
            display.value = "Clock up"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMissionDialog(
    newMissionName: MutableState<String>,
    newMissionDuration: MutableState<String>,
    showDialog: MutableState<Boolean>,
    missionList: MutableList<Mission>
) {
    AlertDialog(
        onDismissRequest = { showDialog.value == false },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        Column() {
            Text(
                text = "New mission's name and desired duration",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            OutlinedTextField(
                value = newMissionName.value,
                onValueChange = { newMissionName.value = it },
                singleLine = true,
                label = { Text("Name") },
            )
            OutlinedTextField(
                value = newMissionDuration.value,
                onValueChange = { newMissionDuration.value = it },
                singleLine = true,
                label = { Text("Duration(min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Row(modifier = Modifier.align(Alignment.End)) {
                Button(
                    onClick = {
                        showDialog.value = false
                        newMissionName.value = "New Mission"
                        newMissionDuration.value = "25"
                    },
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        showDialog.value = false
                        missionList.add(
                            Mission(
                                newMissionName.value,
                                Duration.ofMinutes(newMissionDuration.value.toLong())
                            )
                        )
                        newMissionName.value = "New Mission"
                        newMissionDuration.value = "25"
                    }
                ) {
                    Text("Confirm")
                }
            }
        }

    }
}
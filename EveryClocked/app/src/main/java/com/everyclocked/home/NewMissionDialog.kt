package com.everyclocked.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.everyclocked.utilclass.Mission
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMissionDialog(
    newMissionName: MutableState<String>,
    newMissionDuration: MutableState<String>,
    showDialog: MutableState<Boolean>,
    newMission: MutableState<Mission?>
) {
    AlertDialog(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(8.dp)),
        onDismissRequest = { showDialog.value = false },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
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
            Row(
                modifier = Modifier.align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
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
                        newMission.value = Mission(
                            newMissionName.value,
                            newMissionDuration.value.toInt() * 60
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

package com.everyclocked.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.everyclocked.utils.ClockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSelection(
    showDialog: MutableState<Boolean>,
    clockVM: ClockViewModel
) {
    AlertDialog(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(8.dp)
        ),
        onDismissRequest = { showDialog.value = false },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
    ) {
        val red = remember {
            mutableStateOf(0f)
        }
        val green = remember {
            mutableStateOf(0f)
        }
        val blue = remember {
            mutableStateOf(0f)
        }
        val color = remember {
            mutableStateOf(Color(red.value, green.value, blue.value))
        }
        LaunchedEffect(key1 = red.value, key2 = green.value, key3 = blue.value) {
            color.value = Color(red.value, green.value, blue.value)
        }
        Column(modifier = Modifier.padding(24.dp))
        {
            Text("Selected color")
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color.value)
                    .size(32.dp)
                    .align(CenterHorizontally)
            ) {}
            Column() {
                Text(
                    text = "Red",
                    textAlign = TextAlign.Start
                )
                Slider(
                    value = red.value,
                    onValueChange = {
                        red.value = it
                    },
                    valueRange = 0f..1f
                )
                Text(
                    text = "Green",
                    textAlign = TextAlign.Start
                )
                Slider(
                    value = green.value,
                    onValueChange = {
                        green.value = it
                    },
                    valueRange = 0f..1f
                )
                Text(
                    text = "Blue",
                    textAlign = TextAlign.Start
                )
                Slider(
                    value = blue.value,
                    onValueChange = {
                        blue.value = it
                    },
                    valueRange = 0f..1f
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    red.value = 0f
                    green.value = 0f
                    blue.value = 0f
                    clockVM.rmCustomColor()
                    showDialog.value = false
                },
            ) {
                Text("Reset Color")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showDialog.value = false
                    }) {
                    Text("Cancel")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        clockVM.setRGB(red.value, green.value, blue.value)
                        showDialog.value = false
                    },
                ) {
                    Text("Apply")
                }
            }
        }
    }
}
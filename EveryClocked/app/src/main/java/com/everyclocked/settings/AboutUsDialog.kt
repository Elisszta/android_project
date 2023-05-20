package com.everyclocked.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUs(showDialog: MutableState<Boolean>) {
    AlertDialog(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(8.dp)),
        onDismissRequest = { showDialog.value = false },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        )
        {
            Text(
                text = "about",
            )
        }
    }
}
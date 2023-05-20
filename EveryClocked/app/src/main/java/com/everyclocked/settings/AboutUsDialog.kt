package com.everyclocked.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUs(showDialog: MutableState<Boolean>) {
    AlertDialog(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(8.dp)
        ),
        onDismissRequest = { showDialog.value = false },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier.padding(24.dp),
        )
        {
            Text(
                text = "About\nThis application is developed by Mrtroll486 and Elisszta\n" +
                        "This is our GitHub Repo:",
                textAlign = TextAlign.Center,
            )
            Text(
                text = "https://github.com/Elisszta/android_project",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable{
                    val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/Elisszta/android_project"))
                    context.startActivity(intent)
                }
            )
        }
    }
}
package com.everyclocked.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.everyclocked.ui.theme.EveryClockedTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Screen") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(45f)
                    .background(Color.LightGray)
                    .padding(top = 70.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.Red,
                        center = Offset(size.width / 2, size.height / 2),
                        radius = size.minDimension / 2
                    )
                }
                Text(
                    text = "Time:HH:MM:SS",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Text(
                text = "time state",
                fontSize = 32.sp,
                modifier = Modifier
                    .weight(5f)
            )
            LazyColumn (
                modifier = Modifier
                    .weight(45f),
                    ){
                items(5) { index ->
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        shape = RoundedCornerShape(15.dp)

                    ) {
                        Row() {
                            Text(
                                text = "Mission $index",
                                modifier = Modifier
                                    .weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "Mission ETF",
                                modifier = Modifier
                                    .weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainPageUI() {
    EveryClockedTheme() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MainPage()
        }
    }
}
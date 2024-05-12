package com.example.internalstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.internalstorage.ui.theme.InternalstorageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternalstorageTheme {
                MyComposable()
            }
        }
    }
}
@Composable
fun MyComposable() {
    var buttonClicks by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello, Sukkur IBA University!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            buttonClicks++
            // Perform any action you want here
        }) {
            Text(text = "Click me ($buttonClicks)")
        }
    }
}
@Preview
@Composable
fun MyComposablePreview() {
    MyComposable()
}
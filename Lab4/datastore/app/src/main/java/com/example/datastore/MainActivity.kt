package com.example.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datastore.ui.theme.DatastoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatastoreTheme {
                CounterScreen()
            }
        }
    }
}
@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel) {
    val counter by viewModel.counter.observeAsState(0)

    Column {
        Text(text = "Counter Value: $counter", style = MaterialTheme.typography.h5)
        Button(onClick = { viewModel.incrementCounter() }) {
            Text("Increment Counter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CounterScreen()
}
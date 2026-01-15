package com.CU.blink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.ui.theme.BlinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlinkTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { TopAppBar(Modifier.padding(24.dp)) }) { innerPadding ->

                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()) {
        Text("Blink", modifier = Modifier.clickable( true, onClick = { print("Hello") }), style = MaterialTheme.typography.titleMedium, fontSize = 24.sp)
        IconButton(onClick = { print("Account Route") }) { Icon(Icons.Filled.AccountCircle, "Account Icon", Modifier.size(24.dp))}
    }
}

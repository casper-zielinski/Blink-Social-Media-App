package com.CU.blink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.ui.theme.BlinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlinkTheme {
                Surface(color = MaterialTheme.colorScheme.background ,modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { TopAppBar(Modifier.padding(24.dp)) }) { innerPadding ->
                        PostSender(Modifier
                            .padding(innerPadding)
                            .fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.app_name),
            modifier = Modifier.clickable(true, onClick = { print("Hello") }),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 24.sp
        )
        IconButton(onClick = { print("Account Route") }) {
            Icon(
                Icons.Filled.AccountCircle,
                "Account Icon",
                Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun PostSender(modifier: Modifier = Modifier) {
    var sendText by remember { mutableStateOf<String>("") }

    Surface(color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    Icons.Filled.AccountCircle,
                    "Post Sender Account Icon",
                    modifier = Modifier.height(46.dp).width(46.dp)
                )
                TextField(
                    value = sendText,
                    onValueChange = { sendText = it },
                    placeholder = { Text(stringResource(R.string.placeholderwhatshappening)) },
                    modifier = Modifier
                )
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), Arrangement.End) {
                ElevatedButton(onClick = { sendText = "" }) {
                    Icon(Icons.Filled.Send, "Icon to Send")
                }
            }
        }
    }

}

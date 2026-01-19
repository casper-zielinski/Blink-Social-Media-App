package com.CU.blink.Account
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun BioSection(bio: String, modifier: Modifier = Modifier,
               bioStyle: TextStyle = MaterialTheme.typography.headlineLarge
) {
    var text by remember { mutableStateOf(bio) }

    Column(modifier) {

        Text(text = "Bio", style = bioStyle)

        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            modifier = Modifier.fillMaxWidth(),
        )

    }
}
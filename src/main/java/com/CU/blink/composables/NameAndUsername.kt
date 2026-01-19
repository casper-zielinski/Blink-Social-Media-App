package com.CU.blink.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun NameAndUsername(name: String,
                    username: String,
                    modifier: Modifier = Modifier,
                    nameStyle: TextStyle = MaterialTheme.typography.bodyLarge,
                    usernameStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Column(modifier) {
        Text(text = name, style = nameStyle)
        Text(text = username, style = usernameStyle)
    }
}
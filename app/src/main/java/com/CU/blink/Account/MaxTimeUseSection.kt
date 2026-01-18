package com.CU.blink.Account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun MaxTimeUseSection(maxTimeUse: Int, modifier: Modifier = Modifier,
                      maxTimeUseStyle: TextStyle = MaterialTheme.typography.headlineLarge) {
    var amount by remember { mutableStateOf(maxTimeUse.toString()) }
    var art by remember {mutableStateOf("h")}

    Column (modifier) {
        Text(text = "Max Zeit", style = maxTimeUseStyle)

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = amount,
                onValueChange = { newAmount: String ->
                    if (newAmount.all { it.isDigit() } || newAmount.isNotEmpty()) {
                        amount = newAmount
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("Dauer") }
            )

            VerticalDivider()

            TextField(
                value = art,
                onValueChange = { newArt: String ->
                    if (newArt.isNotEmpty()) {
                        amount = newArt
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("Art") }
            )

        }
    }
}
package com.CU.blink.Account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxTimeUseSection(
    maxTimeUse: Long,
    modifier: Modifier = Modifier,
    maxTimeUseStyle: TextStyle = MaterialTheme.typography.headlineLarge
) {
    var amount by remember { mutableStateOf(msToDisplay(maxTimeUse).first.toString()) }
    var timeUnit by remember { mutableStateOf(msToDisplay(maxTimeUse).second) }
    var maxTimeMs by remember { mutableLongStateOf(maxTimeUse) }

    var expanded by remember { mutableStateOf(false) }
    val units = listOf("s", "min", "h")

    Column(modifier) {

        Text(text = "Max Zeit", style = maxTimeUseStyle)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = amount,
                onValueChange = { newAmount: String ->
                    if (newAmount.all { it.isDigit() }) {
                        amount = newAmount
                        maxTimeMs = convertToMs(newAmount.toIntOrNull() ?: 0, timeUnit)
                    }
                },
                modifier = Modifier.weight(1f),
                label = { Text("Dauer") }
            )


            Box(modifier = Modifier.width(8.dp))


            Box(modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = timeUnit,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Art") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        units.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    timeUnit = selectionOption
                                    maxTimeMs = convertToMs(amount.toIntOrNull() ?: 0, timeUnit)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun convertToMs(amount: Int, unit: String): Long {
    return when (unit) {
        "h" -> amount * 60 * 60 * 1000L
        "min" -> amount * 60 * 1000L
        "s" -> amount * 1000L
        else -> amount * 1000L
    }
}

fun msToDisplay(ms: Long): Pair<Int, String> {
    return if (ms >= 60 * 60 * 1000L) {
        (ms / (60 * 60 * 1000L)).toInt() to "h"
    } else if (ms >= 60 * 1000L) {
        (ms / (60 * 1000L)).toInt() to "min"
    } else {
        (ms / 1000L).toInt() to "s"
    }
}
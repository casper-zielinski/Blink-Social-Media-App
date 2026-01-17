package com.CU.blink.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AccountIcon(modifier: Modifier = Modifier, contentDescription: String) {
    Icon(Icons.Filled.AccountCircle, contentDescription, modifier = modifier)
}
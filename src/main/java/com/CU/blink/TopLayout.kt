package com.CU.blink

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
public fun TopAppBarBlink(modifier: Modifier = Modifier) {
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
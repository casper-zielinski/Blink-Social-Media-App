package com.CU.blink

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun BottomAppBarBlink(modifier: Modifier = Modifier, onChange: (value: PageLocation) -> Unit, currentPageLocation: PageLocation) {
    NavigationBar(modifier) {
        PageLocation.entries.forEach { it ->
            NavigationBarItem(it == currentPageLocation, onClick = { onChange(it) }, icon = { Icon(
                it.icon, it.text) }, label = { Text(it.text) })
        }
    }
}

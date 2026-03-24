package com.CU.blink.Account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.CU.blink.ThemeViewModel

@Composable
fun DesignSection( modifier: Modifier = Modifier,   statsStyle: TextStyle = MaterialTheme.typography.headlineLarge, viewModel: ThemeViewModel) {
    Column(modifier) {
        Text(text = "Design", style = statsStyle)

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = if(viewModel.isDarkMode) "Dunkler Modus" else "Heller Modus")

            Spacer(modifier = Modifier.height(16.dp))

            Switch(
                checked = viewModel.isDarkMode,
                onCheckedChange = { viewModel.toggleTheme() }
            )
        }
    }
}
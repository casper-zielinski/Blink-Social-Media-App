package com.CU.blink.Account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.CU.blink.R
import com.CU.blink.ThemeViewModel

@Composable
fun DesignSection( modifier: Modifier = Modifier,   statsStyle: TextStyle = MaterialTheme.typography.headlineLarge, viewModel: ThemeViewModel) {
    Column(modifier) {
        Text(text = stringResource(R.string.design_title), style = statsStyle)

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = if(viewModel.isDarkMode) stringResource(R.string.dark_mode_label) else stringResource(R.string.light_mode_label))

            Spacer(modifier = Modifier.height(16.dp))

            Switch(
                checked = viewModel.isDarkMode,
                onCheckedChange = { viewModel.toggleTheme() }
            )
        }
    }
}
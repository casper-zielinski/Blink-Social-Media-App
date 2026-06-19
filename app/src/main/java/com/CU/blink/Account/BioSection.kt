package com.CU.blink.Account
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.CU.blink.R

@Composable
fun BioSection(bio: String, modifier: Modifier = Modifier,
               bioStyle: TextStyle = MaterialTheme.typography.headlineLarge
) {
    var text by remember { mutableStateOf(bio) }

    Column(modifier) {

        Text(text = stringResource(R.string.bio_title), style = bioStyle)

        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
        )

    }
}
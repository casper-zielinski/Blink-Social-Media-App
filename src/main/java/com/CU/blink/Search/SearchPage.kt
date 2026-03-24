package com.CU.blink.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import com.CU.blink.User.UserData
import com.CU.blink.composables.AccountIcon
import com.CU.blink.composables.NameAndUsername

@Composable
fun SearchPage(modifier: Modifier = Modifier) {

    var text by remember { mutableStateOf("") }
    val users = listOf(
        UserData("Ai", "Demir555"),
        UserData("Gemini", "GoogleAI"),
        UserData("Android", "Bot123"),
        UserData("Compose", "UI_Master"),
        UserData("Kotlin", "JetBrains")
    )

    val filteredUsers = users.filter {
        it.name.contains(text, ignoreCase = true) || it.username.contains(text, ignoreCase = true)
    }

    Column(modifier = modifier.fillMaxSize()) {

        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Search...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            singleLine = true,

            // Search Icon instead of Enter icon on keyboard
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            // Icon in the Textfield
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },

            // X icon to delete the text
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        LazyColumn(modifier = Modifier
            .fillMaxSize()) {
            items(filteredUsers) {user ->
                Row(modifier = Modifier
                    .fillMaxSize()
                    .clickable{}
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
                    ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AccountIcon(
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Account Icon"
                        )
                    }

                        NameAndUsername(
                            name = user.name,
                            username = user.username,
                            Modifier.padding(start = 8.dp),
                            nameStyle = MaterialTheme.typography.headlineMedium,
                            usernameStyle = MaterialTheme.typography.labelLarge,
                        )

                }
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }

    }
}
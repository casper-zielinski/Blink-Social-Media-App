package com.CU.blink.HomePage

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.CU.blink.R
import com.CU.blink.composables.AccountIcon

@Composable
fun CommentFeed(modifier: Modifier, comments: List<Comment>?, viewModel: SocialViewModel = viewModel(), postid: String) {
    Column(modifier) {
        var sendText by remember { mutableStateOf("") }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AccountIcon(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp), "Post Sender Account Icon"
            )
            TextField(
                value = sendText,
                onValueChange = { sendText = it },
                placeholder = { Text(stringResource(R.string.commentplacholder)) },
                modifier = Modifier
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp), Arrangement.End
        ) {
            ElevatedButton(onClick = { viewModel.addComment(postid, sendText); sendText = "" }) {
                Icon(Icons.AutoMirrored.Filled.Send, "Icon to Send")
            }
        }

        if (comments == null || comments.isEmpty()) {
            Text(
                "No Comments yet",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
        } else {
            comments.forEach {
                Column(
                ) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        AccountIcon(
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp), "Account Icon of${it.name}"
                        )
                        NameAndUsername(it.name, it.username, Modifier.padding(start = 8.dp))
                    }
                    Text(
                        it.content,
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(top = 6.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}



package com.CU.blink.HomePage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.R
import com.CU.blink.composables.AccountIcon
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun homePage(modifier: Modifier = Modifier, viewModel: SocialViewModel = viewModel()) {
    Column(modifier) {
        PostSender(
            Modifier.fillMaxWidth(), viewModel
        )
        PostFeed(Modifier.fillMaxWidth(), viewModel)
    }
}

@Composable
fun PostSender(modifier: Modifier = Modifier, viewModel: SocialViewModel) {
    var sendText by remember { mutableStateOf("") }

    Surface(
        color = NavigationBarDefaults.containerColor, modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AccountIcon(
                    modifier = Modifier
                        .height(46.dp)
                        .width(46.dp), "Post Sender Account Icon"
                )
                TextField(
                    value = sendText,
                    onValueChange = { sendText = it },
                    placeholder = { Text(stringResource(R.string.placeholderwhatshappening)) },
                    modifier = Modifier
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp), Arrangement.End
            ) {
                ElevatedButton(onClick = { viewModel.addPost(sendText); sendText = "" }) {
                    Icon(Icons.AutoMirrored.Filled.Send, "Icon to Send")
                }
            }
        }
    }
}

@Composable
fun PostFeed(modifier: Modifier = Modifier, viewModel: SocialViewModel) {
    val mainListState = rememberLazyListState()
    val posts by viewModel.posts.collectAsState()
    val errorMsg by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(errorMsg) {
        if (errorMsg != null) {
            Toast.makeText(context, "Fehler: $errorMsg", Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    LazyColumn(state = mainListState, modifier = modifier.fillMaxWidth()) {
        itemsIndexed(posts) { index, it ->
            Spacer(modifier = Modifier.padding(if (index == 0) 3.dp else 1.5.dp))
            SinglePost(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor), it, viewModel, mainListState, index
            )
            Spacer(modifier = Modifier.padding(if (posts.size - 1 == index) 3.dp else 1.5.dp))
        }
    }
}

@Composable
fun SinglePost(
    modifier: Modifier, post: Post, viewModel: SocialViewModel, mainListState: LazyListState,
    postIndex: Int
) {
    var showComment by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier
            .clickable(onClick = { showComment = !showComment })
            .padding(horizontal = 12.dp, vertical = 20.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AccountIcon(
                modifier = Modifier
                    .height(46.dp)
                    .width(46.dp), "Account Icon of${post.name}"
            )
            NameAndUsername(post.name, post.username, Modifier.padding(start = 8.dp))
        }
        Text(
            post.content,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(top = 6.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        if (showComment) {
            viewModel.loadCommentsForPost(post.id)
            val comments by viewModel.comments.collectAsState()

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            var sendText by remember { mutableStateOf("") }
            val coroutineScope = rememberCoroutineScope()

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
                    modifier = Modifier.onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                delay(300)
                                mainListState.animateScrollToItem(postIndex)
                            }
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp), Arrangement.End
            ) {
                ElevatedButton(onClick = {
                    viewModel.addComment(post.id, sendText); sendText = ""
                }) {
                    Icon(Icons.AutoMirrored.Filled.Send, "Icon to Send")
                }
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                modifier = Modifier.padding(top = 6.dp)
            )
            CommentFeed(Modifier.padding(8.dp), comments[post.id])
        }
    }

}

@Composable
fun NameAndUsername(name: String, username: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(name, style = MaterialTheme.typography.bodyLarge)
        Text(
            username,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
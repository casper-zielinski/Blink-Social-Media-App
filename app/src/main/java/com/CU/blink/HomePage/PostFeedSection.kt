package com.CU.blink.HomePage

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.CU.blink.R
import com.CU.blink.Upload.NetworkStatus
import com.CU.blink.Upload.PendingUploadStore
import com.CU.blink.composables.AccountIcon
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import com.CU.blink.composables.NameAndUsername
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun homePage(modifier: Modifier = Modifier, viewModel: SocialViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    Box(modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            PostSender(
                Modifier.fillMaxWidth(), viewModel, snackbarHostState
            )
            UploadStatusBanner(Modifier.fillMaxWidth())
            PostFeed(Modifier.fillMaxWidth(), viewModel)
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/*
 * AI ASSISTANCE NOTICE — coded with AI
 *
 * Motivation: we used an AI tool for this background-upload feature mainly because we
 *   wanted to see what an Android Service and BroadcastReceiver look like in a real app,
 *   and to learn from a working example.
 *
 * Why AI was needed:
 *   Collecting the upload queue and the online state as Compose state (collectAsState) to
 *   drive this banner was new to us.
 * Written by us (the team):
 *   The idea of a banner that tells the user a post is queued / waiting for internet, and
 *   its German wording.
 * Supported by the AI tool:
 *   Wiring the StateFlows into the composable and the conditional rendering.
 */
/**
 * Shows the current upload state while posts are queued: an offline hint while there is no
 * connection, or an "uploading" hint once we are back online and the service is working.
 */
@Composable
fun UploadStatusBanner(modifier: Modifier = Modifier) {
    val pending by PendingUploadStore.pending.collectAsState()
    val isOnline by NetworkStatus.isOnline.collectAsState()

    if (pending.isNotEmpty()) {
        val message = if (isOnline) {
            "Post wird hochgeladen…"
        } else {
            "Kein Internet – wird gesendet sobald online"
        }
        Surface(color = MaterialTheme.colorScheme.secondaryContainer, modifier = modifier) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun PostSender(
    modifier: Modifier = Modifier,
    viewModel: SocialViewModel,
    snackbarHostState: SnackbarHostState
) {
    var sendText by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    /*
     * AI ASSISTANCE NOTICE — coded with AI (the image-picking + permission block below)
     *
     * Motivation: we used an AI tool for this background-upload feature mainly because we
     *   wanted to see what an Android Service and BroadcastReceiver look like in a real app,
     *   and to learn from a working example.
     *
     * Why AI was needed:
     *   Picking an image needs the runtime gallery permission, which differs by Android
     *   version (READ_MEDIA_IMAGES vs READ_EXTERNAL_STORAGE), plus the Activity Result
     *   launchers for both the permission request and the system picker. This Compose +
     *   permission flow was unfamiliar to us.
     * Written by us (the team):
     *   The feature — let the user attach a photo to a post, with a preview and a friendly
     *   message if access is denied.
     * Supported by the AI tool:
     *   The version-dependent permission choice and the rememberLauncherForActivityResult
     *   wiring for the picker and the permission request.
     */
    // The right permission to read images depends on the Android version.
    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Opens the system image picker; the chosen image is shown as a preview.
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> if (uri != null) selectedImage = uri }

    // Asks for the gallery permission; on grant we open the picker, on denial we explain why.
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            pickImageLauncher.launch("image/*")
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "Ohne Galerie-Zugriff kann kein Foto ausgewählt werden."
                )
            }
        }
    }

    fun onPickPhotoClick() {
        val alreadyGranted = ContextCompat.checkSelfPermission(
            context, galleryPermission
        ) == PackageManager.PERMISSION_GRANTED
        if (alreadyGranted) {
            pickImageLauncher.launch("image/*")
        } else {
            permissionLauncher.launch(galleryPermission)
        }
    }

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

            // Preview of the picked image; tap it to remove it again.
            selectedImage?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Ausgewähltes Bild (zum Entfernen tippen)",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { selectedImage = null }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onPickPhotoClick() }) {
                    Icon(Icons.Filled.AddCircle, "Foto hinzufügen")
                }
                ElevatedButton(onClick = {
                    viewModel.addPost(sendText, selectedImage?.toString(), context)
                    sendText = ""
                    selectedImage = null
                }) {
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
        if (post.content.isNotBlank()) {
            Text(
                post.content,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(top = 6.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        // AI ASSISTANCE NOTICE — coded with AI: loading the post's hosted image from its URL
        // uses the Coil AsyncImage composable, which the AI tool suggested. The idea to show
        // the attached image inside a post is ours.
        if (post.imageUrl.isNotBlank()) {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = "Bild von ${post.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
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


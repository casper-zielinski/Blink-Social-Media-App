package com.CU.blink.Account

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.CU.blink.R
import com.CU.blink.composables.NameAndUsername
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HeaderSection(
    headerImageUrl: String,
    userImageUrl: String,
    name: String,
    username: String,
    modifier: Modifier = Modifier,
) {
    val baseContext = LocalContext.current
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.aq),
            contentDescription = "Header Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = "User's image",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(Color.Black)
                .border(4.dp, Color.LightGray, CircleShape)
        )
        NameAndUsername(
            name = name,
            username = username,
            Modifier
                .align(Alignment.BottomStart)
                .padding(top = 24.dp, bottom = 8.dp, start = 8.dp),
            nameStyle = MaterialTheme.typography.headlineMedium,
            usernameStyle = MaterialTheme.typography.labelLarge,
        )
    }
}

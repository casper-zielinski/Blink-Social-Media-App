package com.CU.blink.Account

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
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
    onSuccessfullyLogout: () -> Unit
) {
    val baseContext = LocalContext.current
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

        Button(
            onClick = {
                try {
                    FirebaseAuth.getInstance().signOut()
                    onSuccessfullyLogout()
                } catch (e: Exception) {
                    Toast.makeText(
                        baseContext,
                        "Fill out all Fields",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }, modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, "Log Out Icon to Log Out From App")
                Text(
                    "Log Out",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

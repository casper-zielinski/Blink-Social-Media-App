package com.CU.blink.Account

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountPage(modifier: Modifier = Modifier, onSuccessfullyLogout: () -> Unit) {

    val baseContext = LocalContext.current
    val userData = FirebaseAuth.getInstance().currentUser
    val name = userData?.displayName
    val email = userData?.email
    val user = User(
        name =  if (name.isNullOrEmpty()) "Your Name" else name,
        email = if (email.isNullOrEmpty()) "" else email,
        username = "Cas3333",
        bio = "I love react",
        maxTimeUse = 1,
        userImageUrl = "",
        headerImageUrl = "",
        likesCount = 1100,
        followersCount = 2,
        followingCount = 33
    );

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSection(
            headerImageUrl = user.headerImageUrl,
            userImageUrl = user.userImageUrl,
            name = user.name,
            username = user.username,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
        )

        BioSection(
            user.bio,
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
            bioStyle = MaterialTheme.typography.headlineMedium
        )

        MaxTimeUseSection(
            user.maxTimeUse,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
            maxTimeUseStyle = MaterialTheme.typography.headlineMedium
        )
        StatsSection(user.likesCount, user.followersCount, user.followingCount)

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
            }, modifier = Modifier.padding(12.dp)
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
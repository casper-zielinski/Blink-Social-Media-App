package com.CU.blink.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.CU.blink.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun accountPage(modifier: Modifier = Modifier, onSuccessfullyLogout: () -> Unit) {
    val baseContext = LocalContext.current
    val currentUser = Firebase.auth.currentUser
    Column(
        modifier = modifier
            .padding(vertical = 24.dp, horizontal = 12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Card(Modifier
            .fillMaxWidth()
            .padding(6.dp)) {
            Text(
                stringResource(R.string.account),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            Row(Modifier.padding(8.dp)) {
                Text("Name: ")
                currentUser?.displayName?.let { Text(it) }
            }
            Row(Modifier.padding(8.dp)) {
                Text("Username: ")
                currentUser?.email?.let { Text(it.split(".")[0]) }
            }
            Row(Modifier.padding(8.dp)) {
                Text("Email: ")
                currentUser?.email?.let { Text(it) }
            }

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
}
package com.CU.blink.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun accountPage(modifier: Modifier = Modifier, onSuccessfullyLogout: () -> Unit) {
    val baseContext = LocalContext.current
    Column(
        modifier = modifier
            .padding(vertical = 24.dp, horizontal = 12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
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
            },
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, "Log Out Icon to Log Out From App")
                Text("Log Out", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}
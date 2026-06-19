package com.CU.blink.Account

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountSettings(modifier: Modifier = Modifier, onSuccessfullyLogout: () -> Unit, onChangingPassword: () -> Unit) {
    val baseContext = LocalContext.current


    Column(modifier = modifier) {
        Text("Account Settings", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
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
                }, modifier = Modifier.padding(10.dp)
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
            Button(
                onClick = {
                    try {
                        onChangingPassword()
                        Toast.makeText(
                            baseContext,
                            "Password reset email sent!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            baseContext,
                            e.message,
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                },
                modifier = Modifier.padding(10.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Password, "Log Out Icon to Log Out From App")
                    Text(
                        "Reset Password",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}
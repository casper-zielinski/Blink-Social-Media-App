package com.CU.blink.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginOrRegister(modifier: Modifier = Modifier, onSuccessfullLogin: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = Firebase.auth
    val baseContext = LocalContext.current

    Card(modifier) {
        Column(
            Modifier
                .padding(6.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.register),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 28.sp,
                modifier = Modifier.padding(12.dp)
            )
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(stringResource(R.string.nameplaceholder)) },
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(stringResource(R.string.emailplaceholder)) },
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(stringResource(R.string.passwordplaceholder)) },
                modifier = Modifier.padding(8.dp)
            )
            FilledTonalButton(onClick = {
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        baseContext,
                        "Fill out all Fields",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val user = auth.currentUser
                                val profileUpdates = userProfileChangeRequest {
                                    displayName = name
                                }

                                user?.updateProfile(profileUpdates)
                                    ?.addOnCompleteListener { updateTask ->
                                        if (updateTask.isSuccessful) {
                                            Toast.makeText(
                                                baseContext,
                                                "Successfully Registered!",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                            onSuccessfullLogin()
                                        } else {
                                            Toast.makeText(
                                                baseContext,
                                                "Authentication failed. Check Internet Connection or Try Again",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed. Check Internet Connection or Try Again",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                }

            }, Modifier.padding(24.dp)) { Text("Sign Up", Modifier.padding(6.dp)) }
        }

    }
}
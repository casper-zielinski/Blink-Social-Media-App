package com.CU.blink.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginPage(modifier: Modifier = Modifier, auth: FirebaseAuth, onSuccessfullyLogin: () -> Unit, onChangePage:  () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val baseContext = LocalContext.current
    Column(
        modifier
            .padding(6.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(R.string.login),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 28.sp,
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
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
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "Fill out all Fields",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                Toast.makeText(
                    baseContext,
                    "Logging In",
                    Toast.LENGTH_SHORT,
                ).show()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Successfully logged In!",
                            Toast.LENGTH_SHORT,
                        ).show()
                        onSuccessfullyLogin()
                    }
                    else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed. Check Internet Connection or Try Again",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }, Modifier.padding(12.dp)) { Text("Sign Up", Modifier.padding(6.dp)) }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onChangePage, Modifier.padding(8.dp)) { Text(stringResource(R.string.register), textDecoration = TextDecoration.Underline) }
    }
}
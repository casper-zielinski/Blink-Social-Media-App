package com.CU.blink.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.CU.blink.composables.AppLogo
import com.CU.blink.composables.Input
import com.CU.blink.composables.PasswordInput

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    onLogin: (String, String, (Boolean, String?) -> Unit) -> Unit,
    isLoading: Boolean,
    onSuccessfullyLogin: () -> Unit,
    onChangePage: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val baseContext = LocalContext.current
    Column(
        modifier
            .padding(6.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo()
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 28.sp,
            modifier = Modifier.padding(12.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        Input(text = email, onTextChange = { email = it }, placeholder = stringResource(R.string.emailplaceholder), imageVector = Icons.Filled.Email, contentDescription = "Email Icon")
        PasswordInput(password = password, onPasswordChange = { password = it }, showPassword = showPassword, onShowPassword = { showPassword = !showPassword })
        FilledTonalButton(
            onClick = {
                onLogin(email, password) { success, error ->
                    if (success) {
                        Toast.makeText(
                            baseContext,
                            "Successfully logged In!",
                            Toast.LENGTH_SHORT,
                        ).show()
                        onSuccessfullyLogin()
                    } else {
                        Toast.makeText(
                            baseContext,
                            error ?: "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            },
            modifier = Modifier.padding(12.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            } else {
                Text("Login", Modifier.padding(6.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onChangePage,
            Modifier.padding(8.dp)
        ) { Text(stringResource(R.string.register), textDecoration = TextDecoration.Underline) }
    }
}

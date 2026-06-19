package com.CU.blink.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
fun RegisterPage(
    modifier: Modifier = Modifier,
    onSignUp: (String, String, String, (Boolean, Int?) -> Unit) -> Unit,
    isLoading: Boolean,
    onSuccessfullyLogin: () -> Unit,
    onChangePage: () -> Unit,
    toForgotPassword: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val baseContext = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier
            .padding(6.dp)
            .fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AppLogo()
        Text(
            stringResource(R.string.register),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 28.sp,
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Input(text = name, onTextChange = { name = it }, placeholder = stringResource(R.string.nameplaceholder), imageVector = Icons.Filled.Person, contentDescription = stringResource(R.string.name_icon_desc))
        Input(text = email, onTextChange = { email = it }, placeholder = stringResource(R.string.emailplaceholder), imageVector = Icons.Filled.Person, contentDescription = stringResource(R.string.email_icon_desc))
        PasswordInput(password = password, onPasswordChange = { password = it }, showPassword = showPassword, onShowPassword = { showPassword = !showPassword })
        FilledTonalButton(
            onClick = {
                onSignUp(name, email, password) { success, error ->
                    if (success) {
                        Toast.makeText(
                            baseContext,
                            baseContext.getString(R.string.register_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        onSuccessfullyLogin()
                    } else {
                        Toast.makeText(
                            baseContext,
                            if (error != null) baseContext.getString(error) else baseContext.getString(R.string.error_register_failed),
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
                Text(stringResource(R.string.signup_button), Modifier.padding(6.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onChangePage,
        ) { Text(stringResource(R.string.login), textDecoration = TextDecoration.Underline) }
        TextButton(
            toForgotPassword,
            Modifier.padding(2.dp)
        ) { Text(stringResource(R.string.forgot_password_link), textDecoration = TextDecoration.Underline) }
    }
}

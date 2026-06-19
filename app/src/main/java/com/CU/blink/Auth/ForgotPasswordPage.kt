package com.CU.blink.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.R
import com.CU.blink.composables.AppLogo
import com.CU.blink.composables.Input

@Composable
fun ForgotPasswordPage(modifier: Modifier = Modifier, onForgotPassword: (String?) -> Unit, toLogin: () -> Unit, toRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    val baseContext = LocalContext.current

    Column(
        modifier
            .padding(6.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AppLogo()
        Text(
            stringResource(R.string.reset_password_button),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 28.sp,
            modifier = Modifier.padding(12.dp)
        )
        Text(stringResource(R.string.reset_password_desc), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.weight(1f))
        Input(text = email, onTextChange = { email = it }, placeholder = stringResource(R.string.emailplaceholder), imageVector = Icons.Filled.Person, contentDescription = stringResource(R.string.email_icon_desc))
        Spacer(modifier = Modifier.weight(0.25f))
        Button(onClick = {
            try {
                onForgotPassword(email)
                Toast.makeText(
                    baseContext,
                    baseContext.getString(R.string.password_reset_sent),
                    Toast.LENGTH_SHORT,
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    baseContext,
                    e.message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }) {
            Text(stringResource(R.string.reset_password_button))
        }
        Spacer(modifier = Modifier.weight(0.6f))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Button(toLogin) {
                Text(stringResource(R.string.login))
            }
            Button(toRegister, colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)) {
                Text(stringResource(R.string.register))
            }
        }
    }
}
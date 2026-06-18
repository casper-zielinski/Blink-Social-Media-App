package com.CU.blink.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.CU.blink.R

@Composable
fun Input(modifier: Modifier = Modifier, text: String, onTextChange: (String) -> Unit, placeholder: String, imageVector: ImageVector, contentDescription: String?, keyboardType: KeyboardType = KeyboardType.Text) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier.padding(8.dp),
        leadingIcon = { Icon(imageVector, contentDescription) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun PasswordInput(modifier: Modifier = Modifier, password: String, onPasswordChange: (String) -> Unit, showPassword: Boolean, onShowPassword: () -> Unit) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text(stringResource(R.string.passwordplaceholder)) },
        modifier = modifier.padding(8.dp),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            IconButton( onClick = onShowPassword) {
                if (showPassword) {
                    Icon(Icons.Filled.Visibility, "Showing Password")
                } else {
                    Icon(Icons.Filled.VisibilityOff, "Hide Password")
                }
            }
        }
    )
}
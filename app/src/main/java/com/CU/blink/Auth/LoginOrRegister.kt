package com.CU.blink.Auth

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun LoginOrRegister(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onLogin: (String, String, (Boolean, Int?) -> Unit) -> Unit,
    onSignUp: (String, String, String, (Boolean, Int?) -> Unit) -> Unit,
    onSuccessfulLogin: () -> Unit
) {
    var currentPage by remember { mutableStateOf(AuthPage.REGISTER) }

    Card(modifier) {
        when (currentPage) {
            AuthPage.LOGIN -> LoginPage(
                modifier = Modifier,
                onLogin = onLogin,
                isLoading = isLoading,
                onSuccessfullyLogin = onSuccessfulLogin,
                onChangePage = {
                    currentPage = AuthPage.REGISTER
                })

            AuthPage.REGISTER -> RegisterPage(
                modifier = Modifier,
                onSignUp = onSignUp,
                isLoading = isLoading,
                onSuccessfullyLogin = onSuccessfulLogin,
                onChangePage = {
                    currentPage = AuthPage.LOGIN
                })
        }
    }
}

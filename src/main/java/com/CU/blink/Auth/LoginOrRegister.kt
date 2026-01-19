package com.CU.blink.Auth

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginOrRegister(modifier: Modifier = Modifier, onSuccessfulLogin: () -> Unit) {

    val auth = Firebase.auth
    var currentPage by remember { mutableStateOf(AuthPage.REGISTER) }

    Card(modifier) {
        when (currentPage) {
            AuthPage.LOGIN -> LoginPage(
                Modifier,
                auth,
                onSuccessfullyLogin = onSuccessfulLogin,
                onChangePage = {
                    currentPage =
                        AuthPage.REGISTER
                })

            AuthPage.REGISTER -> RegisterPage(
                Modifier,
                auth,
                onSuccessfullyLogin = onSuccessfulLogin,
                onChangePage = {
                    currentPage =
                        AuthPage.LOGIN
                })
        }
    }
}
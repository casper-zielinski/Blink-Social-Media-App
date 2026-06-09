package com.CU.blink

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CU.blink.Auth.LoginOrRegister
import com.CU.blink.Account.AccountPage
import com.CU.blink.HomePage.homePage
import com.CU.blink.Search.SearchPage
import com.CU.blink.User.UserViewModel
import com.CU.blink.ui.theme.BlinkTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private lateinit var auth: FirebaseAuth

class MainActivity : ComponentActivity() {

    // Registered once; the result is ignored on purpose so we never nag the user again
    // (Android only shows the system dialog the first time anyway).
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        requestNotificationPermissionIfNeeded()
        val currentUser = auth.currentUser
        val themeViewModel by viewModels<ThemeViewModel>()
        val userViewModel by viewModels<UserViewModel>()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var page by rememberSaveable { mutableStateOf(PageLocation.HOME) }
            var loggedIn by rememberSaveable { mutableStateOf(currentUser != null) }

            BlinkTheme(darkTheme = themeViewModel.isDarkMode) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    if (loggedIn) {
                        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                            TopAppBarBlink(
                                modifier = Modifier.padding(
                                    top = 20.dp, bottom = 8.dp, start = 8.dp, end = 8.dp
                                ), onChange = { page = it })
                        }, bottomBar = {
                            BottomAppBarBlink(
                                modifier = Modifier.fillMaxWidth(),
                                onChange = { page = it },
                                page
                            )
                        }) { innerPadding ->
                            when (page) {
                                PageLocation.SEARCH -> SearchPage(Modifier.padding(innerPadding))
                                PageLocation.HOME -> homePage(Modifier.padding(innerPadding))
                                PageLocation.ACCOUNT -> AccountPage(
                                    modifier = Modifier.padding(innerPadding),
                                    onSuccessfullyLogout = {
                                        userViewModel.logOut { loggedIn = false }
                                    },
                                    viewModel = themeViewModel,
                                    userViewModel = userViewModel
                                )
                            }
                        }
                    } else {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            LoginOrRegister(
                                modifier = Modifier
                                    .padding(46.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.6f),
                                isLoading = userViewModel.isLoading,
                                onLogin = { email, password, onResult ->
                                    userViewModel.login(email, password, onResult)
                                },
                                onSignUp = { name, email, password, onResult ->
                                    userViewModel.signUp(name, email, password, onResult)
                                },
                                onSuccessfulLogin = { loggedIn = true })
                        }
                    }
                }
            }

            LaunchedEffect(loggedIn) {
                if (loggedIn) {
                    userViewModel.loadUserData()
                }
            }
        }
    }

    /**
     * Asks for the notification permission once on Android 13+ so the upload service's
     * progress notification can be shown. The system only displays the dialog on the first
     * request, so a declined permission is not asked about again.
     */
    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}




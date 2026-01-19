package com.CU.blink.Account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.ThemeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountPage(modifier: Modifier = Modifier, onSuccessfullyLogout: () -> Unit, viewModel: ThemeViewModel) {

<<<<<<< HEAD
    val baseContext = LocalContext.current
    val userData = FirebaseAuth.getInstance().currentUser
    val name = userData?.displayName
    val email = userData?.email
    val user = User(
        name = if (name.isNullOrEmpty()) "Your Name" else name,
        email = if (email.isNullOrEmpty()) "" else email,
        username = if (email.isNullOrEmpty()) "" else email.split("@")[0],
        bio = "I love react",
        maxTimeUse = 1,
        userImageUrl = "",
        headerImageUrl = "",
        likesCount = 1100,
        followersCount = 2,
        followingCount = 33
    );
=======
        var user by remember { mutableStateOf<User?>(null) }

        var isLoading by remember {mutableStateOf(true)}

        // dummy data
    /*
    *  val user = User(
            name = "Cas",
            username = "Cas3333",
            bio = "I love react",
            maxTimeUse = 14444,
            userImageUrl = "",
            headerImageUrl = "",
            likesCount = 1100,
            followersCount = 2,
            followingCount = 33
        );*/
    LaunchedEffect(Unit) {
        UserRepository.getCurrentUser { loadedUser ->
            user = loadedUser
            isLoading = false
        }}

    if (isLoading) {
        LoadingScreen()
    } else {

        val activeUser = user ?: User()
>>>>>>> ui

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderSection(
<<<<<<< HEAD
                name = user.name,
                username = user.username,
=======
                headerImageUrl = activeUser.headerImageUrl,
                userImageUrl = activeUser.userImageUrl,
                name = activeUser.name,
                username =  activeUser.username,
>>>>>>> ui
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    ,
                onSuccessfullyLogout
            )
<<<<<<< HEAD

            BioSection(
                user.bio,
=======
            BioSection(activeUser.bio,
>>>>>>> ui
                Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    .padding(16.dp),
                bioStyle = MaterialTheme.typography.headlineMedium
            )

<<<<<<< HEAD
            MaxTimeUseSection(
                user.maxTimeUse,
=======
            MaxTimeUseSection(activeUser.maxTimeUse, modifier = Modifier
                .fillMaxWidth()
                .background(NavigationBarDefaults.containerColor)
                .padding(16.dp),
                maxTimeUseStyle =  MaterialTheme.typography.headlineMedium)

            StatsSection(activeUser.likesCount, activeUser.followersCount, activeUser.followingCount,
>>>>>>> ui
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    .padding(16.dp),
                maxTimeUseStyle = MaterialTheme.typography.headlineMedium
            )

            StatsSection(
                user.likesCount,
                user.followersCount,
                user.followingCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    .padding(16.dp),
                statsStyle = MaterialTheme.typography.headlineMedium
            )

            DesignSection(modifier = Modifier
                .fillMaxWidth()
                .background(NavigationBarDefaults.containerColor)
                .padding(16.dp), statsStyle = MaterialTheme.typography.headlineMedium, viewModel)
        }
<<<<<<< HEAD
=======
    }




>>>>>>> ui
    }
}
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
    fun AccountPage(modifier: Modifier = Modifier) {

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

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderSection(
                headerImageUrl = activeUser.headerImageUrl,
                userImageUrl = activeUser.userImageUrl,
                name = activeUser.name,
                username =  activeUser.username,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    .padding(16.dp)
            )
            BioSection(activeUser.bio,
                Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    .padding(16.dp),
                bioStyle = MaterialTheme.typography.headlineMedium)

            MaxTimeUseSection(activeUser.maxTimeUse, modifier = Modifier
                .fillMaxWidth()
                .background(NavigationBarDefaults.containerColor)
                .padding(16.dp),
                maxTimeUseStyle =  MaterialTheme.typography.headlineMedium)

            StatsSection(activeUser.likesCount, activeUser.followersCount, activeUser.followingCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    .padding(16.dp),
                statsStyle =  MaterialTheme.typography.headlineMedium)
        }
    }




    }

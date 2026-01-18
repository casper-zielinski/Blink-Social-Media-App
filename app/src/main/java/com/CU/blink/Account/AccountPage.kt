package com.CU.blink.Account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
    fun AccountPage(modifier: Modifier = Modifier) {

        // dummy data
        val user = User(
            name = "Cas",
            username = "Cas3333",
            bio = "I love react",
            maxTimeUse = 1,
            userImageUrl = "",
            headerImageUrl = "",
            likesCount = 1100,
            followersCount = 2,
            followingCount = 33
        );

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection(
                headerImageUrl = user.headerImageUrl,
                userImageUrl = user.userImageUrl,
                name = user.name,
                username =  user.username,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp)
            )
            BioSection(user.bio,
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp),
                bioStyle = MaterialTheme.typography.headlineMedium)

            MaxTimeUseSection(user.maxTimeUse, modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
                maxTimeUseStyle =  MaterialTheme.typography.headlineMedium)
            StatsSection(user.likesCount, user.followersCount, user.followingCount)
        }


    }

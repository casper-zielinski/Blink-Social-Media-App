package com.CU.blink.Account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
    fun AccountPage(modifier: Modifier = Modifier) {

        // dummy data
        val user = User(
            name = "Cas",
            username = "Cas",
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
            HeaderSection(user.headerImageUrl, user.userImageUrl)
            NameSection(user.name, user.username)
            BioSection(user.bio)
            MaxTimeUseSection(user.maxTimeUse)
            StatsSection(user.likesCount, user.followersCount, user.followingCount)
        }


    }

package com.CU.blink.Account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CU.blink.ThemeViewModel
import com.CU.blink.User.User
import com.CU.blink.User.UserRepository
import com.CU.blink.User.UserViewModel

@Composable
fun AccountPage(modifier: Modifier = Modifier,
                onSuccessfullyLogout: () -> Unit,
                viewModel: ThemeViewModel,
                userViewModel: UserViewModel) {

    val user = userViewModel.currentUser;
    val isLoading = userViewModel.isLoading;

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
                    .padding(16.dp),
                onSuccessfullyLogout = onSuccessfullyLogout
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
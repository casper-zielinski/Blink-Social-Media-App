package com.CU.blink

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class PageLocation {
    SEARCH, HOME, ACCOUNT;

    val icon : ImageVector
        get() = when (this) {
            SEARCH -> Icons.Filled.Search
            HOME -> Icons.Filled.Home
            ACCOUNT -> Icons.Filled.AccountCircle
        }

    val text : String
        get() = when (this) {
            SEARCH -> "Search"
            HOME -> "Home"
            ACCOUNT -> "Account"
        }
}
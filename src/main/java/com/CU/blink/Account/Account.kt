package com.CU.blink.Account

data class User(
    val name: String,
    val username: String,
    val bio: String,
    val userImageUrl: String,
    val headerImageUrl: String,
    val maxTimeUse: Int,
    val likesCount: Int,
    val followersCount: Int,
    val followingCount: Int
)

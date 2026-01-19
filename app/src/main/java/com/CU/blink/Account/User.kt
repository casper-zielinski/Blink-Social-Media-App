package com.CU.blink.Account

data class User(
    val id: String = "", // Wir speichern die UID mit im Objekt
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val userImageUrl: String = "",
    val headerImageUrl: String = "",
    val maxTimeUse: Long = 0L,
    val likesCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0
)

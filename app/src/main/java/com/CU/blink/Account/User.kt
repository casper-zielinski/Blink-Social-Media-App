package com.CU.blink.Account

data class User(
<<<<<<< HEAD
    val name: String,
    val username: String,
    val email: String,
    val bio: String,
    val userImageUrl: String,
    val headerImageUrl: String,
    val maxTimeUse: Long,
    val likesCount: Int,
    val followersCount: Int,
    val followingCount: Int
=======
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
>>>>>>> ui
)

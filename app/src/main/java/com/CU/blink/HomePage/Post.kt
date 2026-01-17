package com.CU.blink.HomePage

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    @DocumentId val id: String = "",
    val content: String = "",
    val name: String = "",
    val username: String = "",
    @ServerTimestamp val postedAt: Date? = null
)
package com.CU.blink.HomePage

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    @DocumentId val id: String = "",
    val content: String = "",
    val name: String = "",
    val username: String = "",
    // AI ASSISTANCE NOTICE — coded with AI: this imageUrl field was added for the upload
    // feature. It stores the public Cloudinary URL of a post's image ("" for text-only posts).
    val imageUrl: String = "",
    @ServerTimestamp val postedAt: Date? = null
)
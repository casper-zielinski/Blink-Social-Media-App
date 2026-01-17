package com.CU.blink.HomePage

import com.google.firebase.firestore.DocumentId

data class Comment(
    @DocumentId val id : String = "",
    val content: String = "",
    val name: String = "",
    val username: String = ""
)

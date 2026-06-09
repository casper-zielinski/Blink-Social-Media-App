package com.CU.blink.Upload

import java.util.UUID

/**
 * A post the user created that still has to be uploaded to Firestore.
 * Persisted by [PendingUploadStore] so it survives leaving the app or going offline.
 */
data class PendingPost(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val name: String,
    val username: String,
    /** Local content URI of the picked image, or null for a text-only post. */
    val imageUri: String? = null
)

/*
 * AI ASSISTANCE NOTICE — coded with AI
 *
 * Motivation: we used an AI tool for this background-upload feature mainly because we
 *   wanted to see what an Android Service and BroadcastReceiver look like in a real app,
 *   and to learn from a working example.
 *
 * Why AI was needed:
 *   Minor — this data class is part of the AI-assisted upload feature and is documented
 *   here for completeness.
 * Written by us (the team):
 *   The fields a queued post needs (text, author, optional image).
 * Supported by the AI tool:
 *   The id/UUID default used to identify a post in the queue.
 */
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

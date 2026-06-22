/*
 * AI ASSISTANCE NOTICE — coded with AI
 *
 * Motivation: we used an AI tool for this background-upload feature mainly because we
 *   wanted to see what an Android Service and BroadcastReceiver look like in a real app,
 *   and to learn from a working example.
 *
 * Why AI was needed:
 *   Persisting a queue of posts so it survives the app being closed or going offline means
 *   serializing the list to JSON, storing it in SharedPreferences, and exposing it as a
 *   StateFlow — the JSON/StateFlow plumbing was unfamiliar to us.
 * Written by us (the team):
 *   The queue concept and its operations (enqueue, peek the next one, remove when done).
 * Supported by the AI tool:
 *   The JSON read/write into SharedPreferences and the StateFlow the UI observes.
 */
package com.CU.blink.Upload

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

/**
 * A small, file-backed queue of posts waiting to be uploaded.
 *
 * It uses SharedPreferences so a queued post survives the user leaving the app or
 * the device going offline. The [pending] flow lets the UI react to the queue
 * (e.g. show "wird gesendet sobald online").
 */
object PendingUploadStore {
    private const val PREFS = "blink_pending_uploads"
    private const val KEY = "pending_posts"

    private val _pending = MutableStateFlow<List<PendingPost>>(emptyList())
    val pending: StateFlow<List<PendingPost>> = _pending.asStateFlow()

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    /** Loads the persisted queue into [pending]. Call once on app start. */
    fun init(context: Context) {
        _pending.value = read(context)
    }

    fun enqueue(context: Context, post: PendingPost) {
        write(context, read(context) + post)
    }

    fun remove(context: Context, id: String) {
        write(context, read(context).filterNot { it.id == id })
    }

    /** The post that should be uploaded next, or null if the queue is empty. */
    fun peek(context: Context): PendingPost? = read(context).firstOrNull()

    fun hasPending(context: Context): Boolean = read(context).isNotEmpty()

    private fun read(context: Context): List<PendingPost> {
        val raw = prefs(context).getString(KEY, null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                PendingPost(
                    id = obj.getString("id"),
                    content = obj.getString("content"),
                    name = obj.getString("name"),
                    username = obj.getString("username"),
                    imageUri = obj.optString("imageUri").ifEmpty { null }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun write(context: Context, posts: List<PendingPost>) {
        val array = JSONArray()
        posts.forEach { post ->
            array.put(
                JSONObject()
                    .put("id", post.id)
                    .put("content", post.content)
                    .put("name", post.name)
                    .put("username", post.username)
                    .put("imageUri", post.imageUri)
            )
        }
        prefs(context).edit().putString(KEY, array.toString()).apply()
        _pending.value = posts
    }
}

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

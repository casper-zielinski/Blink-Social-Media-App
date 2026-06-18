package com.CU.blink.HomePage

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.CU.blink.Auth.User
import com.CU.blink.Upload.BlinkUploadService
import com.CU.blink.Upload.PendingPost
import com.CU.blink.Upload.PendingUploadStore
import java.io.File
import java.io.FileOutputStream
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.core.net.toUri

class SocialViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _comments = MutableStateFlow<HashMap<String, List<Comment>>>(hashMapOf())
    val comments = _comments.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val postCollection = db.collection("Posts")

    val user = Firebase.auth.currentUser

    init {
        loadPosts()
    }

    fun loadPosts() {
        postCollection.orderBy("postedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _errorMessage.value = error.message
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _posts.value = snapshot.toObjects<Post>()
                }
            }
    }

    /**
     * Queues the post and hands it to [BlinkUploadService] for upload. The post is not added
     * to the feed directly: once the service has uploaded it, the Firestore snapshot listener
     * in [loadPosts] picks it up. While offline it stays queued and is sent on reconnect.
     */
    fun addPost(content: String, imageUri: String? = null, context: Context) {
        val name = user?.displayName
        val username = user?.email?.split("@")?.firstOrNull()
        val hasContent = content.isNotBlank() || imageUri != null

        if (user != null && hasContent && !name.isNullOrEmpty() && !username.isNullOrEmpty()) {
            // Copy the picked image into our own storage so the upload still works even if the
            // post is queued (offline) and the app is restarted before it can be sent.
            val storedImage = imageUri?.let { copyImageToInternalStorage(context, it) }
            PendingUploadStore.enqueue(
                context,
                PendingPost(content = content, name = name, username = username, imageUri = storedImage)
            )
            BlinkUploadService.start(context)
            loadPosts() // to show the newest posts to the user
        }
    }

    /** Copies the content at [imageUri] into the app's files dir and returns a file:// URI. */
    private fun copyImageToInternalStorage(context: Context, imageUri: String): String? {
        return try {
            val file = File(context.filesDir, "upload_${System.currentTimeMillis()}.jpg")
            context.contentResolver.openInputStream(imageUri.toUri())?.use { input ->
                FileOutputStream(file).use { output -> input.copyTo(output) }
            } ?: return null
            Uri.fromFile(file).toString()
        } catch (_: Exception) {
            null
        }
    }

    fun getCommentCollectionPath(postId: String): CollectionReference {
        return db.collection("Posts/$postId/Comments")
    }

    fun loadCommentsForPost(postId: String) {
        getCommentCollectionPath(postId).orderBy("postedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _errorMessage.value = error.message
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _comments.value[postId] = snapshot.toObjects<Comment>()
                }
            }
    }

    fun addComment(postId: String, content: String) {
        val currentUser= User(user?.displayName, user?.email?.split(".")[0], user?.email)

        if (user != null && !currentUser.name.isNullOrEmpty() && !currentUser.username.isNullOrEmpty()) {
            val newComment = Comment(content = content, username = currentUser.username, name = currentUser.name)
            getCommentCollectionPath(postId).add(newComment)

            _comments.update { currentComment ->
                val updatedMap = HashMap(currentComment)

                val existingComments = updatedMap[postId] ?: emptyList()

                updatedMap[postId] = existingComments + newComment

                updatedMap
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

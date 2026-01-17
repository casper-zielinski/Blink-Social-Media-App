package com.CU.blink.HomePage

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.emptyMap

class SocialViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _comments = MutableStateFlow<HashMap<String, List<Comment>>>(hashMapOf())
    val comments = _comments.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val postCollection = db.collection("Posts")

    init {
        loadPosts()
    }

    fun loadPosts() {
        postCollection.orderBy("postedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _errorMessage.value = error.message
                    print(_errorMessage.value)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _posts.value = snapshot.toObjects<Post>()
                    print(_posts.value)
                }
            }
    }

    fun addPost(content: String) {
        val newPost : Post = Post(content = content,name = "Casper", username = "casper.zielinski")
        postCollection.add(newPost)
        _posts.value += newPost
    }

    fun getCommentCollectionPath(postId: String) : CollectionReference {
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
        val newComment : Comment = Comment(content = content, username = "casper.zielinski", name = "Casper")
        getCommentCollectionPath(postId).add(newComment)

        _comments.update { currentComment ->
            val updatedMap = HashMap(currentComment)

            val existingComments = updatedMap[postId] ?: emptyList()

            updatedMap[postId] = existingComments + newComment

            updatedMap
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

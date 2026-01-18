package com.CU.blink.HomePage

import androidx.lifecycle.ViewModel
import com.CU.blink.Auth.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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

    val user = Firebase.auth.currentUser

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
        val currentUser : User = User(user?.displayName, user?.email?.split(".")[0], user?.email)

        if (user != null && !currentUser.name.isNullOrEmpty() && !currentUser.username.isNullOrEmpty() ) {
            val newPost : Post = Post(content = content,name = currentUser.name, username = currentUser.username)
            postCollection.add(newPost)
            _posts.value += newPost
        }
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

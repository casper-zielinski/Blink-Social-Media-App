package com.CU.blink.User

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.CU.blink.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // set will ensure that the "state" is immutable for outside classes
    var currentUser by  mutableStateOf<User?>(null)
    private set

    var isLoading by mutableStateOf(false)
    private set

    fun loadUserData() {
        val uid = auth.currentUser?.uid

        if (uid != null) {
            isLoading = true

            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    currentUser = document.toObject(User::class.java)
                    isLoading = false
                }
                .addOnFailureListener {
                    isLoading = false
                }
        } else {
            currentUser = null
            isLoading = false
        }
    }

    fun logOut(onComplete: () -> Unit) {
        auth.signOut()
        currentUser = null
        onComplete()
    }

    fun login(email: String, password: String, onComplete: (Boolean, Int?) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onComplete(false, R.string.error_fill_fields)
            return
        }
        if (password.length < 8 || !email.contains("@")) {
            onComplete(false, R.string.error_invalid_credentials)
            return
        }

        isLoading = true
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loadUserData() // Update currentUser state
                    isLoading = false
                    onComplete(true, null)
                } else {
                    isLoading = false
                    // We can't easily map Firebase exceptions to resources without a big when block,
                    // but we can provide a generic error or just pass the message.
                    // Let's stick to resource IDs for validation and a generic one for auth failure
                    // unless we want to keep String for dynamic Firebase messages.
                    // Given the goal of resource files, let's use a generic error resource for Auth exceptions.
                    onComplete(false, R.string.error_auth_failed)
                }
            }
    }

    fun signUp(name: String, email: String, password: String, onComplete: (Boolean, Int?) -> Unit) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            onComplete(false, R.string.error_fill_fields)
            return
        }
        if (password.length < 8 || !email.contains("@")) {
            onComplete(false, R.string.error_invalid_credentials)
            return
        }

        isLoading = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val firebaseUser = auth.currentUser
                if (task.isSuccessful && firebaseUser != null) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    firebaseUser.updateProfile(profileUpdates)
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                val newUser = User(
                                    id = firebaseUser.uid,
                                    name = name,
                                    email = email,
                                    username = email.split("@")[0],
                                    bio = "Neu bei Blink!"
                                )

                                db.collection("users").document(firebaseUser.uid)
                                    .set(newUser)
                                    .addOnSuccessListener {
                                        isLoading = false
                                        currentUser = newUser
                                        onComplete(true, null)
                                    }
                                    .addOnFailureListener {
                                        firebaseUser.delete().addOnCompleteListener {
                                            isLoading = false
                                            onComplete(false, R.string.error_save_user)
                                        }
                                    }
                            } else {
                                firebaseUser.delete().addOnCompleteListener {
                                    isLoading = false
                                    onComplete(false, R.string.error_profile_rollback)
                                }
                            }
                        }
                } else {
                    isLoading = false
                    onComplete(false, R.string.error_register_failed)
                }
            }
    }

    fun resetPassword(email: String? = null) {

        val receivedEmail = email ?: currentUser?.email
        receivedEmail?.let {
            auth.sendPasswordResetEmail(receivedEmail).addOnSuccessListener {
                return@addOnSuccessListener
            }.addOnFailureListener {
                throw Exception(it.message)
            }
        }
    }
}
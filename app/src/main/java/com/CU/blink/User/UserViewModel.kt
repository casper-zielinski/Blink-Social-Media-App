package com.CU.blink.User

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
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

    // 2. Die "Fetch"-Action (Wie ein Async Thunk)
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

    fun updateUser(updatedUser: User, onComplete: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid)
            .set(updatedUser)
            .addOnSuccessListener {
                currentUser = updatedUser
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    // Necessary for the "slice" so we can actually set it to null if logged out
    fun logOut(onComplete: () -> Unit) {
        auth.signOut()
        currentUser = null
        onComplete()
    }

    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onComplete(false, "Fill out all Fields")
            return
        }
        if (password.length < 8 || !email.contains("@")) {
            onComplete(false, "Use a valid Password and Email")
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
                    onComplete(false, task.exception?.message ?: "Authentication failed")
                }
            }
    }

    fun signUp(name: String, email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            onComplete(false, "Fill out all Fields")
            return
        }
        if (password.length < 8 || !email.contains("@")) {
            onComplete(false, "Use a valid Password and Email")
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

                                // Datenbank-Eintrag erstellen
                                db.collection("users").document(firebaseUser.uid)
                                    .set(newUser)
                                    .addOnSuccessListener {
                                        isLoading = false
                                        currentUser = newUser
                                        onComplete(true, null)
                                    }
                                    .addOnFailureListener { e ->
                                        // FEHLER-FALL: Wenn DB fehlschlägt, Auth-User wieder löschen!
                                        firebaseUser.delete().addOnCompleteListener {
                                            isLoading = false
                                            onComplete(false, "Database error: ${e.message}. Account rolled back.")
                                        }
                                    }
                            } else {
                                // Wenn Profil-Update fehlschlägt, Auth-User auch löschen
                                firebaseUser.delete().addOnCompleteListener {
                                    isLoading = false
                                    onComplete(false, "Profile update failed. Account rolled back.")
                                }
                            }
                        }
                } else {
                    isLoading = false
                    onComplete(false, task.exception?.message ?: "Registration failed")
                }
            }
    }

    fun resetPassword() {
        val email = currentUser?.email
        email?.let {
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                return@addOnSuccessListener
            }.addOnFailureListener {
                throw Exception(it.message)
            }
        }
    }
}
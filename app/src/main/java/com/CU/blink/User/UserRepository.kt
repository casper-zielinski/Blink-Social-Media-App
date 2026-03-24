package com.CU.blink.User

// In UserRepository.kt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object UserRepository {
    private val db get() = FirebaseFirestore.getInstance()
    private val auth get() = FirebaseAuth.getInstance()

    // 1. Uploud user
    fun saveUser(user: User, onComplete: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid)
                .set(user.copy(id = uid))
                .addOnSuccessListener { onComplete(true) }
                .addOnFailureListener { onComplete(false) }
        }
    }

    // 2. Load user
    fun getCurrentUser(onResult: (User?) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    val user = document.toObject(User::class.java)
                    onResult(user)
                }
                .addOnFailureListener { onResult(null) }
        } else {
            onResult(null)
        }
    }
}


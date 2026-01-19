package com.CU.blink.User

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class UserViewModel: ViewModel() {

    // set will ensure that the "state" is immutable for outside classes
    var currentUser by  mutableStateOf<User?>(null)
    private set

    var isLoading by mutableStateOf(false)
    private set

    // 2. Die "Fetch"-Action (Wie ein Async Thunk)
    fun loadUserData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            isLoading = true

            UserRepository.getCurrentUser { user ->
                currentUser = user
                isLoading = false
            }
        } else {
            currentUser = null
            isLoading = false
        }
    }

    fun updateUser(updatedUser: User, onComplete: (Boolean) -> Unit) {
        UserRepository.saveUser(updatedUser) { success ->
            if (success) {
                currentUser = updatedUser
            }
            onComplete(success)
        }
    }

    // Necessary for the "slice" so we can actually set it to null if logged out
    fun logOut(onComplete: () -> Unit) {
        FirebaseAuth.getInstance().signOut()

        currentUser = null;

        onComplete();
    }
}
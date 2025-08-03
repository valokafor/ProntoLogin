package com.valokafor.prontologin.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.valokafor.prontologin.data.model.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult(isSuccess = true, message = "Login successful")
        } catch (e: FirebaseAuthException) {
            AuthResult(
                isSuccess = false,
                message = getFirebaseErrorMessage(e),
                exception = e
            )
        } catch (e: Exception) {
            AuthResult(
                isSuccess = false,
                message = "An unexpected error occurred",
                exception = e
            )
        }
    }

    suspend fun signUp(email: String, password: String, fullName: String): AuthResult {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            
            // Update the user's display name
            authResult.user?.let { user ->
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()
                user.updateProfile(profileUpdates).await()
            }
            
            AuthResult(isSuccess = true, message = "Account created successfully")
        } catch (e: FirebaseAuthException) {
            AuthResult(
                isSuccess = false,
                message = getFirebaseErrorMessage(e),
                exception = e
            )
        } catch (e: Exception) {
            AuthResult(
                isSuccess = false,
                message = "An unexpected error occurred",
                exception = e
            )
        }
    }

    suspend fun resetPassword(email: String): AuthResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthResult(isSuccess = true, message = "Password reset email sent")
        } catch (e: FirebaseAuthException) {
            AuthResult(
                isSuccess = false,
                message = getFirebaseErrorMessage(e),
                exception = e
            )
        } catch (e: Exception) {
            AuthResult(
                isSuccess = false,
                message = "An unexpected error occurred",
                exception = e
            )
        }
    }

    private fun getFirebaseErrorMessage(exception: FirebaseAuthException): String {
        return when (exception.errorCode) {
            "ERROR_INVALID_EMAIL" -> "Invalid email address"
            "ERROR_WRONG_PASSWORD" -> "Incorrect password"
            "ERROR_USER_NOT_FOUND" -> "No account found with this email"
            "ERROR_USER_DISABLED" -> "This account has been disabled"
            "ERROR_TOO_MANY_REQUESTS" -> "Too many failed attempts. Try again later"
            "ERROR_EMAIL_ALREADY_IN_USE" -> "An account with this email already exists"
            "ERROR_WEAK_PASSWORD" -> "Password is too weak"
            else -> exception.message ?: "Authentication failed"
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}

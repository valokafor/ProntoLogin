package com.valokafor.prontologin.data.repository

import com.google.firebase.auth.FirebaseUser
import com.valokafor.prontologin.domain.AuthResult

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String, name: String): AuthResult
    suspend fun sendPasswordReset(email: String): AuthResult
    fun getCurrentUser(): FirebaseUser?
    fun isUserAuthenticated(): Boolean
}

package com.valokafor.prontologin.data.repository

import com.valokafor.prontologin.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String): Result<User>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun signOut()
    fun getCurrentUser(): User?
    fun isUserLoggedIn(): Boolean
    fun observeAuthState(): Flow<User?>
}
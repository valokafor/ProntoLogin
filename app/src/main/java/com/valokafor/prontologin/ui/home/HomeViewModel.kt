package com.valokafor.prontologin.ui.home

import androidx.lifecycle.ViewModel
import com.valokafor.prontologin.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun isUserAuthenticated(): Boolean {
        return authRepository.isUserAuthenticated()
    }

    fun getUserDisplayName(): String {
        return authRepository.getCurrentUser()?.displayName ?: "User"
    }
}

package com.valokafor.prontologin.domain.usecase

import com.valokafor.prontologin.data.repository.AuthRepository
import com.valokafor.prontologin.domain.model.User
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.signUp(email, password)
    }
}
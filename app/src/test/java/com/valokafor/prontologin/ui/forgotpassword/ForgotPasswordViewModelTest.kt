package com.valokafor.prontologin.ui.forgotpassword

import com.valokafor.prontologin.data.repository.AuthRepository
import com.valokafor.prontologin.domain.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ForgotPasswordViewModelTest {

    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mock()
        viewModel = ForgotPasswordViewModel(authRepository)
    }

    @Test
    fun `sendPasswordResetEmail with valid email returns success`() = runTest {
        val email = "test@example.com"
        whenever(authRepository.sendPasswordReset(email)).thenReturn(AuthResult.Success)

        viewModel.sendPasswordResetEmail(email)

        assertEquals(ForgotPasswordUiState.Success, viewModel.uiState.value)
    }

    @Test
    fun `sendPasswordResetEmail with invalid email returns error`() = runTest {
        val email = "invalid_email"
        val errorMessage = "Invalid email"
        whenever(authRepository.sendPasswordReset(email)).thenReturn(AuthResult.Error(errorMessage))

        viewModel.sendPasswordResetEmail(email)

        assertEquals(ForgotPasswordUiState.Error(errorMessage), viewModel.uiState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

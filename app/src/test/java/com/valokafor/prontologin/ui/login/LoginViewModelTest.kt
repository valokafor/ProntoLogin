package com.valokafor.prontologin.ui.login

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
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mock()
        viewModel = LoginViewModel(authRepository)
    }

    @Test
    fun `signIn with valid credentials returns success`() = runTest {
        val email = "test@example.com"
        val password = "password"
        whenever(authRepository.signIn(email, password)).thenReturn(AuthResult.Success)

        viewModel.signIn(email, password)

        assertEquals(LoginUiState.Success, viewModel.uiState.value)
    }

    @Test
    fun `signIn with invalid credentials returns error`() = runTest {
        val email = "test@example.com"
        val password = "wrong_password"
        val errorMessage = "Invalid credentials"
        whenever(authRepository.signIn(email, password)).thenReturn(AuthResult.Error(errorMessage))

        viewModel.signIn(email, password)

        assertEquals(LoginUiState.Error(errorMessage), viewModel.uiState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

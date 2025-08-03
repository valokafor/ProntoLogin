package com.valokafor.prontologin.ui.signup

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
class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mock()
        viewModel = SignUpViewModel(authRepository)
    }

    @Test
    fun `signUp with valid credentials returns success`() = runTest {
        val email = "test@example.com"
        val password = "password"
        whenever(authRepository.signUp(email, password)).thenReturn(AuthResult.Success)

        viewModel.signUp(email, password)

        assertEquals(SignUpUiState.Success, viewModel.uiState.value)
    }

    @Test
    fun `signUp with invalid credentials returns error`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val errorMessage = "An error occurred"
        whenever(authRepository.signUp(email, password)).thenReturn(AuthResult.Error(errorMessage))

        viewModel.signUp(email, password)

        assertEquals(SignUpUiState.Error(errorMessage), viewModel.uiState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

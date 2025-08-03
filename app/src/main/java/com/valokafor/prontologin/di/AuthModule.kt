package com.valokafor.prontologin.di

import com.google.firebase.auth.FirebaseAuth
import com.valokafor.prontologin.data.repository.AuthRepository
import com.valokafor.prontologin.data.repository.AuthRepositoryImpl
import com.valokafor.prontologin.domain.usecase.ResetPasswordUseCase
import com.valokafor.prontologin.domain.usecase.SignInUseCase
import com.valokafor.prontologin.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    
    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth)
    
    @Provides
    fun provideSignInUseCase(
        authRepository: AuthRepository
    ): SignInUseCase = SignInUseCase(authRepository)
    
    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository
    ): SignUpUseCase = SignUpUseCase(authRepository)
    
    @Provides
    fun provideResetPasswordUseCase(
        authRepository: AuthRepository
    ): ResetPasswordUseCase = ResetPasswordUseCase(authRepository)
}
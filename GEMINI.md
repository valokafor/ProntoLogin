# Gemini Project Context

This document provides context for the ProntoLogin Android project.

## Project Overview

The goal of this project is to build a functional login system for an Android application named "Pronto Task". The implementation will use modern Android development practices, including Jetpack Compose for the UI, Firebase for authentication, and an MVVM architecture.

## Key Technologies & Libraries

- **UI:** Jetpack Compose with Material Design 3.
- **Architecture:** Model-View-ViewModel (MVVM)
  - **ViewModel:** `androidx.lifecycle.lifecycle-runtime-ktx`
  - **State Management:** Sealed UI state classes to manage loading, success, and error states.
- **Dependency Injection:** Hilt (`com.google.dagger.hilt.android`) for managing dependencies.
- **Authentication:** Firebase Authentication (`com.google.firebase:firebase-auth`) for email/password sign-in, sign-up, and password reset.
- **Navigation:** Jetpack Navigation for Compose (`androidx.navigation.navigation-compose`) to move between screens.
- **Build System:** Gradle with Kotlin DSL. Dependencies are managed in `gradle/libs.versions.toml`.

## Core Features

1.  **Login Screen:**
    -   Email and password fields.
    -   "Sign In", "Sign Up", and "Forgot Password" actions.
    -   Input validation and error feedback.
    -   Loading indicators for asynchronous operations.

2.  **Sign-Up Screen:**
    -   Allows new users to register with an email and password.
    -   Navigates back to the login screen upon successful registration.

3.  **Forgot Password Screen:**
    -   Allows users to request a password reset email.
    -   Provides feedback on success or failure.

## Project Structure

-   The application will follow standard Android project structure.
-   UI will be built entirely with Jetpack Compose functions.
-   Each screen (Login, Sign-Up, Forgot Password) will have its own ViewModel.
-   State will be hoisted from Composables to the ViewModels.
-   The initial `MainActivity` will be updated to host the navigation graph for the authentication flow, removing any placeholder content.

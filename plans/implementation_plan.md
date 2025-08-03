# Pronto Task - Implementation Plan

This document outlines the step-by-step plan to implement the login functionality for the Pronto Task Android application, as described in `docs/pronto_task_login_prompt.md`.

## Phase 1: Project Setup and Configuration

1.  **Verify Dependencies**: Ensure all necessary dependencies for Jetpack Compose, Navigation, Hilt, and Firebase are correctly added to `app/build.gradle.kts` and are available in `gradle/libs.versions.toml`.
2.  **Hilt Setup**:
    *   Create a custom `Application` class and annotate it with `@HiltAndroidApp`.
    *   Update `AndroidManifest.xml` to use this custom Application class.
    *   Create a Hilt module (`AppModule.kt`) to provide the `FirebaseAuth` instance.

## Phase 2: Core Components (Domain and Data)

1.  **Authentication Repository**:
    *   Create a `data` package.
    *   Define an `AuthRepository` interface with methods for `signIn`, `signUp`, and `sendPasswordReset`.
    *   Create an `AuthRepositoryImpl` that implements the interface and interacts with the `FirebaseAuth` instance.
2.  **UI State Management**:
    *   Define a sealed class for each screen's UI state (e.g., `LoginUiState`, `SignUpUiState`). These will represent `Idle`, `Loading`, `Success`, and `Error` states.

## Phase 3: UI Implementation (Composables)

1.  **Create UI Packages**: Organize UI components into packages: `ui.login`, `ui.signup`, `ui.forgotpassword`, and `ui.theme`.
2.  **Login Screen (`LoginScreen.kt`)**:
    *   Create a composable with `OutlinedTextField` for email and password.
    *   Add `Button`s for "Sign In", "Sign Up", and a `TextButton` for "Forgot Password".
    *   Use `Scaffold` for the basic layout.
    *   Implement state hoisting for the input fields.
    *   Display a `CircularProgressIndicator` when the UI state is `Loading`.
    *   Show error messages based on the UI state.
3.  **Sign-Up Screen (`SignUpScreen.kt`)**:
    *   Similar to the Login screen, with email and password fields and a "Sign Up" button.
    *   Include a "Back to Login" option.
4.  **Forgot Password Screen (`ForgotPasswordScreen.kt`)**:
    *   A simple screen with an email field and a "Send Reset Email" button.
    *   Include a "Back to Login" option.

## Phase 4: ViewModel Implementation

1.  **Create ViewModels**:
    *   `LoginViewModel.kt`: Injected with `AuthRepository`. Manages the state for the login screen, handles sign-in logic, and exposes a `StateFlow` of `LoginUiState`.
    *   `SignUpViewModel.kt`: Manages the sign-up flow.
    *   `ForgotPasswordViewModel.kt`: Manages the password reset flow.
2.  **Implement Business Logic**:
    *   In each ViewModel, implement the corresponding methods (`signIn`, `signUp`, etc.) that call the `AuthRepository`.
    *   Update the `StateFlow` with the appropriate UI state (`Loading`, `Success`, `Error`) based on the result from the repository.
    *   Add validation logic for email and password fields, disabling the action buttons if the form is invalid.

## Phase 5: Navigation

1.  **Create Navigation Graph**:
    *   Create a `navigation` package.
    *   Define a sealed class `Screen` with routes for `Login`, `SignUp`, and `ForgotPassword`.
    *   Create a `NavGraph.kt` file to define the `NavHost` and the composable destinations.
2.  **Implement Navigation Actions**:
    *   Use the `NavController` to navigate between screens.
    *   For example, on the `LoginScreen`, the "Sign Up" button will navigate to the `SignUp` route.
    *   After a successful sign-up or password reset, navigate back to the `Login` screen using `navController.popBackStack()`.

## Phase 6: Finalizing `MainActivity`

1.  **Clean Up `MainActivity`**: Remove the default "Hello World" composable and any other placeholder code.
2.  **Set Content**: In `onCreate`, call `setContent` and host the `NavGraph` composable. The `NavGraph` will handle which screen is displayed first.

## Phase 7: Testing and Refinement

1.  **Unit Tests**: Write unit tests for the ViewModels to verify the business logic and state transitions. Mock the `AuthRepository`.
2.  **UI Tests**: Create basic UI tests to ensure that the screens are displayed correctly and that navigation works as expected.
3.  **Manual Testing**:
    *   Test the complete user flow: sign-up, sign-in, sign-out (if implemented), and password reset.
    *   Verify that loading indicators and error messages are displayed correctly.
    *   Check form validation behavior.
4.  **Code Review and Refactoring**: Review the code for adherence to best practices, clarity, and maintainability. Refactor as needed.

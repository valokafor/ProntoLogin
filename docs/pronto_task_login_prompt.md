
# 🧠 AI Agent Prompt – Android Login Screen with Firebase Auth (Pronto Task)

## 📱 Objective
Create a **login screen** for an Android app called **Pronto Task** using **Jetpack Compose** and **Firebase Authentication**. The login screen should support **sign in**, **sign up**, and **forgot password** functionality with proper UI feedback and validation.

---

## 🧰 Tech Stack & Constraints

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with ViewModel and View State
- **Design System**: Material Design 3 using ProntoLoginTheme
- **Auth**: Firebase Authentication (Firebase already initialized)
- **Base Project**: Blank Android app with `MainActivity` and a "Hello World" placeholder that should be removed

---

## 🎨 UI Requirements

- **Login Screen**
  - Fields: Email, Password
  - Buttons: Sign In, Sign Up, Forgot Password
  - Input validation for empty/invalid fields
  - Display appropriate error messages
  - Show loading indicator during network request
  - Show success state on successful login

- **Navigation**
  - Navigate to Sign Up and Forgot Password from Login Screen
  - Return to Login Screen after successful Sign Up or Password Reset

---

## 🔧 Functional Requirements

- **Home Screen**
  - Default screen, if not login go to login screen, else
  - Show Display name from Firebase with Welcome message
  - Put placeholder coming soon message

- **Sign In**
  - Use FirebaseAuth to sign in with email and password
  - Handle loading, success, and error states
  - Navigate to home screen after success

- **Sign Up**
  - Allow new users to create an account with email and password
  - Add full name field and after signup, call Firebase to set the display name to the name vale
  - Reuse email/password validation rules
  - Navigate to home after success

- **Forgot Password**
  - Allow user to request a password reset email
  - Show confirmation or error message

---

## 📂 Structure

Follow modern Android best practices:
- Use a **ViewModel** for each screen
- Use a **sealed UI state class** to represent loading, success, and error states
- Use **State Hoisting** to manage form input
- Use `Scaffold`, `OutlinedTextField`, and `Button` components from Material3

---

## 🧪 Validation

- Email must be a valid format
- Password must be non-empty (or meet Firebase minimum length if enforced)
- Disable Sign In/Sign Up button if form is invalid

---

## 🧼 Clean Up

- Remove the default Greetings and GreetingPreview content from `MainActivity`
- Replace with proper navigation and the login screen implementation

---

## ✅ Final Deliverable

- Working Jetpack Compose UI with navigation between login, sign-up, and forgot password
- Firebase Auth integrated using email/password
- Form validation and feedback
- ViewModel-based state management

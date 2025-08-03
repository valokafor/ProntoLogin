# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ProntoLogin is an Android application implementing authentication screens with Firebase integration. The project is in the initial setup phase with a complete Material Design 3 theme system and Firebase configuration ready for authentication implementation.

## Common Commands

### Build and Run
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install and run on connected device/emulator
./gradlew installDebug

# Clean build
./gradlew clean

# Build and run all checks
./gradlew build
```

### Testing
```bash
# Run all unit tests
./gradlew test

# Run instrumented Android tests
./gradlew connectedAndroidTest

# Run tests for specific build variant
./gradlew testDebugUnitTest
./gradlew testReleaseUnitTest

# Run tests with coverage
./gradlew createDebugCoverageReport
```

### Code Quality
```bash
# Run lint checks
./gradlew lint

# Run lint and generate HTML report
./gradlew lintDebug

# View lint results
# Reports are generated in: app/build/reports/lint-results-debug.html
```

### Dependency Management
```bash
# Check for dependency updates
./gradlew dependencyUpdates

# Refresh dependencies
./gradlew --refresh-dependencies
```

## Architecture and Structure

### Module Structure
- **app/** - Main application module
  - **src/main/java/com/valokafor/prontologin/** - Kotlin source code
    - **ui/theme/** - Material Design 3 theme implementation (Color.kt, Theme.kt, Type.kt)
  - **src/main/res/** - Resources including custom fonts (Montserrat, Roboto families)
  - **google-services.json** - Firebase configuration (present and configured)

### Key Technical Stack
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM pattern (prepared for implementation)
- **Dependency Injection**: Dagger Hilt (configured)
- **Authentication**: Firebase Auth (SDK integrated)
- **Min SDK**: 29 (Android 10)
- **Target SDK**: 36 (Android 15)
- **Kotlin**: 2.2.0 with Compose compiler

### Build Configuration
The project uses Gradle version catalogs (libs.versions.toml) for centralized dependency management. Key configurations:
- ProGuard rules configured for release builds
- Firebase Crashlytics integration ready
- Compose options with Kotlin compiler extensionEnabled

### Theme System
Complete Material Design 3 implementation with:
- Comprehensive color palette (40+ color definitions)
- Custom typography using Montserrat and Roboto font families
- Light theme configured (dark theme ready for implementation)
- Edge-to-edge display support

## Current Implementation Status

### Completed Setup
- Project structure and Gradle configuration
- Firebase integration with google-services.json
- Material Design 3 theme system
- Custom font integration (8 font variants)
- Testing framework setup
- Lint configuration

### Pending Implementation
Based on requirements in `/docs/pronto_task_login_prompt.md`:
1. Login screen UI with email/password fields
2. Sign up screen with form validation
3. Forgot password screen
4. Firebase Authentication implementation
5. ViewModels for state management
6. Navigation between auth screens
7. Form validation and error handling
8. Loading states and user feedback

## Firebase Configuration
- google-services.json is present and configured
- Firebase Authentication SDK is integrated
- Ready for email/password authentication implementation

## Development Notes
- The project follows Android best practices with proper separation of concerns
- Version catalog (libs.versions.toml) manages all dependencies
- ProGuard configuration is ready for release builds
- The codebase is set up for scalability with Hilt dependency injection
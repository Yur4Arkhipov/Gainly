# Gainly
---

## About

**Gainly** is an Android application designed to help users track their workouts, monitor their progress, and stay connected with their friends through fitness.

The project is built as a **multi-module Android application** with a clear separation between application, feature, and core modules. The current codebase includes authentication, onboarding and friends features, while the project continues to evolve.

## Features

### Authentication (email, google, telegram)
- Sign in
- Sign up
- OTP-related flow
- Logout
- Token management

### Onboarding
- Dedicated onboarding module
- Splash screen integration
- Application entry flow

### Home
- Currently under development

### Friends
- Friends list
- Add friends
- Search friends
- Check friends progress and workouts

### Profile
- Currently under development

---

## Architecture

Gainly uses a **multi-module architecture** designed to keep features isolated and reduce coupling between unrelated parts of the application.

### Module responsibilities

| Module                | Responsibility                                                    |
|-----------------------|-------------------------------------------------------------------|
| `:app`                | Application entry point, navigation root, application-level setup |
| `:feature:auth`       | Sign in, sign up and authentication flows                         |
| `:feature:onboarding` | First-launch/onboarding experience                                |
| `:feature:home`       | Home screen and related presentation logic                        |
| `:feature:profile`    | User profile functionality                                        |
| `:feature:friends`    | Friends and adding-friends functionality                          |
| `:core:domain`        | Domain models and use cases                                       |
| `:core:data`          | Data layer, repositories and remote/local implementations         |
| `:core:util`          | Shared utilities and UI/result abstractions                       |
| `:core:designsystem`  | Shared theme and reusable design-system components                |

The project dependencies are declared centrally through Gradle and the modules are connected from `settings.gradle.kts`.

---

## Tech Stack

### Android
- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **AndroidX Activity / Lifecycle**
- **SplashScreen API**

### Architecture & DI
- **Multi-module architecture**
- **ViewModel**
- **Hilt**
- **KSP**
- **Use Case / Repository approach**

### Networking & Serialization
- **Retrofit**
- **OkHttp**
- **OkHttp Logging Interceptor**
- **kotlinx.serialization**
- **Gson converter**

### Local Storage
- **DataStore Preferences**

### Testing
- **JUnit**
- **Mockito / Mockito-Kotlin**
- **AndroidX JUnit**
- **Espresso**
- **Compose UI testing**

---

## 📁 Project Structure

```text
Gainly/
├── app/
│   └── Application entry point
│
├── core/
│   ├── data/
│   │   └── Data layer and repositories
│   ├── designsystem/
│   │   └── Theme and reusable UI components
│   ├── domain/
│   │   └── Domain models and use cases
│   └── util/
│       └── Shared utilities and common abstractions
│
├── feature/
│   ├── auth/
│   │   ├── signin/
│   │   └── signup/
│   ├── friends/
│   ├── home/
│   ├── onboarding/
│   └── profile/
│
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

---

## Testing

The project includes dependencies for:

- Unit tests
- Android instrumented tests
- Compose UI tests

---

## Screenshots

You can see the screenshots of the application in the `screenshots` folder.
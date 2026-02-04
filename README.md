# Passwordless Authentication App (Email + OTP)

This project implements a **passwordless authentication flow** using **Email + OTP**,
built with **Kotlin**, **Jetpack Compose**, and **ViewModel-based architecture**.

The application demonstrates clean state management, OTP handling without a backend,
and a session screen that tracks login duration.

---

## ✨ Features

- Email-based OTP login (no password)
- 6-digit OTP generation
- OTP expiry handling (60 seconds)
- Maximum 3 OTP validation attempts
- Resend OTP support (invalidates old OTP)
- Session screen with live duration timer
- Logout functionality
- Analytics logging using Timber

---

## 🔐 OTP Logic & Expiry Handling

OTP handling is implemented completely on the client side using a dedicated
`OtpManager` class.

### OTP Storage
- OTPs are stored in a `Map<String, OtpData>`
- **Key** → Email address
- **Value** → `OtpData` object containing:
    - OTP value
    - Creation timestamp
    - Remaining attempts

This ensures **one OTP per email** and allows easy invalidation when a new OTP is generated.

### OTP Expiry
- OTP expiry is handled using `System.currentTimeMillis()`
- During validation, the current time is compared with the OTP creation time
- If the difference exceeds **60 seconds**, the OTP is treated as expired

This approach avoids timers or background threads and keeps the logic simple and reliable.

### OTP Attempts
- Each OTP allows a maximum of **3 validation attempts**
- On every failed attempt, the attempt count is reduced
- Once attempts reach zero, validation is blocked until a new OTP is generated

---

## ⏱️ Session Screen & Timer

- On successful login, the session start time is recorded
- Session duration is calculated using the difference between current time and session start time
- A `LaunchedEffect` is used to update the timer every second
- The timer survives recompositions and stops correctly on logout

---

## 📊 Analytics & Logging

**Timber** is used as the external SDK for analytics and logging.

### Why Timber?
- Lightweight and easy to integrate
- No account or backend configuration required
- Commonly used in real-world Android projects
- Suitable for logging lifecycle and authentication events

### Logged Events
- OTP generated
- OTP validation success
- OTP validation failure
- Logout event

All analytics calls are centralized inside a dedicated `AnalyticsLogger` object
to maintain clean separation of concerns.

---

## 🧱 Architecture & State Management

- **UI Layer**: Jetpack Compose screens (stateless, state-driven)
- **ViewModel**: Handles business logic and state updates
- **State Holder**: `AuthState` acts as a single source of truth
- **Data Layer**: `OtpManager` handles OTP rules and validation

The project follows **one-way data flow**:
UI → ViewModel → State → UI

---

## 🧭 Navigation Approach

No Navigation library is used.

Navigation is implemented using **state-based conditional rendering**:
- Login Screen → OTP Screen → Session Screen

This approach keeps navigation simple, readable, and aligned with Compose best practices.

---

## 📝 Commit Strategy

I initially focused on implementing the complete authentication flow and core logic
to ensure correctness and architectural clarity.

Once the architecture was stable, the project was committed as a base implementation.
Subsequent improvements, fixes, and documentation were added incrementally with
clear and meaningful commit messages.

This approach reflects a real-world workflow where functionality is prioritized
before refinement.

---

## 🤖 AI Assistance Disclosure

GPT was used as a **learning and guidance tool** for:
- Understanding best practices
- Structuring the project
- Clarifying Compose and ViewModel concepts

All core logic, implementation decisions, and code were **understood, adapted,
and written manually** based on my understanding.

---

## ▶️ How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle
4. Run the app on an emulator or physical device

No additional setup is required.

---

## 📌 Tech Stack

- Kotlin
- Jetpack Compose
- ViewModel
- Coroutines
- Timber (Analytics / Logging)

---

## ✅ Assignment Notes

- No backend is used
- No global mutable state
- No blocking calls on the main thread
- Clear separation of UI and business logic

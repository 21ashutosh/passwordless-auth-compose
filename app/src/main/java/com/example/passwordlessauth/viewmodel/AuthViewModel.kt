package com.example.passwordlessauth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.passwordlessauth.analytics.AnalyticsLogger
import com.example.passwordlessauth.data.OtpManager
import com.example.passwordlessauth.data.OtpResult

class AuthViewModel : ViewModel() {

    private val otpManager = OtpManager()

    var state by mutableStateOf(AuthState())
        private set

    fun sendOtp(email: String) {
        otpManager.generateOtp(email)
        AnalyticsLogger.otpGenerated(email)

        state = state.copy(
            email = email,
            isOtpSent = true,
            error = null
        )
    }

    fun verifyOtp(enteredOtp: String) {
        when (val result = otpManager.validateOtp(state.email, enteredOtp)) {

            is OtpResult.Success -> {
                AnalyticsLogger.otpValidationSuccess(state.email)
                state = state.copy(
                    isLoggedIn = true,
                    sessionStartTime = System.currentTimeMillis(),
                    error = null
                )
            }

            is OtpResult.Failure -> {
                AnalyticsLogger.otpValidationFailure(state.email)
                state = state.copy(
                    error = "Invalid OTP. Attempts left: ${result.attemptsLeft}"
                )
            }

            OtpResult.Expired -> {
                state = state.copy(error = "OTP expired. Please resend.")
            }

            OtpResult.AttemptsExceeded -> {
                state = state.copy(error = "Maximum attempts exceeded.")
            }

            OtpResult.NoOtp -> {
                state = state.copy(error = "Please request OTP first.")
            }
        }
    }

    fun logout() {
        AnalyticsLogger.logout(state.email)
        state = AuthState()
    }
}

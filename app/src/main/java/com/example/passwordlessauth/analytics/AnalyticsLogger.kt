package com.example.passwordlessauth.analytics

import timber.log.Timber

object AnalyticsLogger {

    /**
     * Called when a new OTP is generated for an email
     */
    fun otpGenerated(email: String) {
        Timber.d("OTP generated for email: $email")
    }

    /**
     * Called when OTP validation is successful
     */
    fun otpValidationSuccess(email: String) {
        Timber.d("OTP validation SUCCESS for email: $email")
    }

    /**
     * Called when OTP validation fails
     */
    fun otpValidationFailure(email: String) {
        Timber.e("OTP validation FAILURE for email: $email")
    }

    /**
     * Called when user logs out
     */
    fun logout(email: String) {
        Timber.d("User logged out. Email: $email")
    }
}

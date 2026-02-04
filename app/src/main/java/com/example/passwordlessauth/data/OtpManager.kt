package com.example.passwordlessauth.data
import timber.log.Timber

/**
 * Holds OTP-related information for a single email
 */
data class OtpData(
    val otp: String,
    val createdAt: Long,
    var attemptsLeft: Int
)

/**
 * Manages OTP generation and validation logic
 */
class OtpManager {

    // Stores OTP data per email
    private val otpStore = mutableMapOf<String, OtpData>()

    /**
     * Generates a new 6-digit OTP for the given email.
     * If an OTP already exists, it will be replaced.
     */
    fun generateOtp(email: String): String {
        val otp = (100000..999999).random().toString()

        otpStore[email] = OtpData(
            otp = otp,
            createdAt = System.currentTimeMillis(),
            attemptsLeft = 3
        )

        // DEBUG ONLY: log OTP since there is no backend
        Timber.d("Generated OTP for $email is $otp")

        return otp
    }

    /**
     * Validates the entered OTP for the given email.
     */
    fun validateOtp(email: String, enteredOtp: String): OtpResult {

        val otpData = otpStore[email]
            ?: return OtpResult.NoOtp

        // Check expiry (60 seconds)
        val isExpired =
            System.currentTimeMillis() - otpData.createdAt > 60_000

        if (isExpired) {
            return OtpResult.Expired
        }

        // Check remaining attempts
        if (otpData.attemptsLeft <= 0) {
            return OtpResult.AttemptsExceeded
        }

        // Check OTP match
        return if (otpData.otp == enteredOtp) {
            otpStore.remove(email)
            OtpResult.Success
        } else {
            otpData.attemptsLeft--
            OtpResult.Failure(otpData.attemptsLeft)
        }
    }
}

/**
 * Represents all possible OTP validation results
 */
sealed class OtpResult {

    object Success : OtpResult()

    object Expired : OtpResult()

    object NoOtp : OtpResult()

    object AttemptsExceeded : OtpResult()

    data class Failure(
        val attemptsLeft: Int
    ) : OtpResult()
}

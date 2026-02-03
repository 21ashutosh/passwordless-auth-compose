package com.example.passwordlessauth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwordlessauth.ui.LoginScreen
import com.example.passwordlessauth.ui.OtpScreen
import com.example.passwordlessauth.ui.SessionScreen
import com.example.passwordlessauth.viewmodel.AuthViewModel

@Composable
fun App(
    viewModel: AuthViewModel = viewModel()
) {
    val state = viewModel.state

    when {
        state.isLoggedIn -> {
            SessionScreen(
                sessionStartTime = state.sessionStartTime!!,
                onLogout = viewModel::logout
            )
        }

        state.isOtpSent -> {
            OtpScreen(
                errorMessage = state.error,
                onVerifyOtp = viewModel::verifyOtp,
                onResendOtp = {
                    viewModel.sendOtp(state.email)
                }
            )
        }

        else -> {
            LoginScreen(
                onSendOtp = viewModel::sendOtp
            )
        }
    }
}

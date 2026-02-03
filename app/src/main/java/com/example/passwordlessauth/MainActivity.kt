package com.example.passwordlessauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.passwordlessauth.ui.theme.PasswordlessauthTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge layout (modern Android practice)
        enableEdgeToEdge()

        // Initialize Timber for logging (External SDK)
        Timber.plant(Timber.DebugTree())
        Timber.d("Timber initialized successfully")

        setContent {
            PasswordlessauthTheme {
                App()
            }
        }
    }
}

package com.example.passwordlessauth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SessionScreen(
    sessionStartTime: Long,
    onLogout: () -> Unit
) {
    var elapsedSeconds by remember { mutableStateOf(0L) }

    LaunchedEffect(sessionStartTime) {
        while (true) {
            elapsedSeconds =
                (System.currentTimeMillis() - sessionStartTime) / 1000
            delay(1000)
        }
    }

    val minutes = elapsedSeconds / 60
    val seconds = elapsedSeconds % 60

    val formattedStartTime = remember(sessionStartTime) {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date(sessionStartTime))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Session Started At",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = formattedStartTime)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Session Duration: %02d:%02d".format(minutes, seconds),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

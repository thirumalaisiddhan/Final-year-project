package com.callguard.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.callguard.ai.ui.navigation.CallGuardAppRoot
import com.callguard.ai.ui.theme.CallGuardAITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val app = application as CallGuardApplication
            val settings by app.repository.getSettings().collectAsState(initial = com.callguard.ai.data.model.AppSettings())
            val isDark = settings.isDarkMode ?: isSystemInDarkTheme()

            CallGuardAITheme(darkTheme = isDark) {
                CallGuardAppRoot()
            }
        }
    }
}

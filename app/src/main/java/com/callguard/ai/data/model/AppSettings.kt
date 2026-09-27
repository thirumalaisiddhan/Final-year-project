package com.callguard.ai.data.model

data class AppSettings(
    val protectionEnabled: Boolean = true,
    val autoBlockingEnabled: Boolean = false,
    val warnBeforeBlocking: Boolean = true,
    val trustedCallerOverride: Boolean = true,
    val deliveryContextEnabled: Boolean = true,
    val unknownCallerWarnings: Boolean = true,
    val riskThreshold: RiskThreshold = RiskThreshold.HIGH,
    val isDarkMode: Boolean? = true // true = dark mode, false = light, null = system
)

package com.callguard.ai.data.model

import androidx.compose.ui.graphics.Color

/**
 * Reusable RiskLevel domain enum used across the entire application.
 * Never hardcode risk colors; query this component/enum.
 */
enum class RiskLevel(
    val title: String,
    val subtitle: String,
    val defaultDecision: CallDecision
) {
    LOW(
        title = "LOW RISK",
        subtitle = "Caller exhibits normal calling pattern",
        defaultDecision = CallDecision.ALLOW
    ),
    MEDIUM(
        title = "MEDIUM RISK",
        subtitle = "Unusual calling volume or repeat short patterns detected",
        defaultDecision = CallDecision.WARN
    ),
    HIGH(
        title = "HIGH RISK",
        subtitle = "Robocall / burst pattern identified",
        defaultDecision = CallDecision.BLOCK
    );

    val badgeColor: Color
        get() = when (this) {
            LOW -> Color(0xFF10B981) // Emerald Green
            MEDIUM -> Color(0xFFF59E0B) // Amber
            HIGH -> Color(0xFFEF4444) // Coral Red
        }

    val containerColor: Color
        get() = when (this) {
            LOW -> Color(0x1A10B981)
            MEDIUM -> Color(0x1AF59E0B)
            HIGH -> Color(0x1AEF4444)
        }

    companion object {
        fun fromScore(score: Int): RiskLevel = when {
            score >= 70 -> HIGH
            score >= 40 -> MEDIUM
            else -> LOW
        }
    }
}

enum class CallDecision(val label: String) {
    ALLOW("Allowed"),
    WARN("Warned"),
    BLOCK("Blocked"),
    REVIEW("Review Required")
}

enum class CallDirection {
    INCOMING,
    OUTGOING,
    MISSED
}

enum class TrustedCategory(val displayName: String) {
    PERSONAL("Personal"),
    DELIVERY("Delivery"),
    FINANCE("Finance"),
    EDUCATION("Education"),
    HEALTHCARE("Healthcare"),
    GOVERNMENT("Government"),
    SERVICE("Service"),
    OTHER("Other")
}

enum class UserFeedbackType(val label: String) {
    CONFIRMED_SPAM("Confirmed Spam"),
    NOT_SPAM("Not Spam")
}

enum class RiskThreshold(val label: String, val thresholdScore: Int) {
    LOW("Low Sensitivity (Score > 80)", 80),
    MEDIUM("Medium Sensitivity (Score > 60)", 60),
    HIGH("High Sensitivity (Score > 40)", 40)
}

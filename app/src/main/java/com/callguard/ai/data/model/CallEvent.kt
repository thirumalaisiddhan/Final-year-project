package com.callguard.ai.data.model

import java.util.UUID

data class CallEvent(
    val id: String = UUID.randomUUID().toString(),
    val phoneNumber: String,
    val callerName: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val timeFormatted: String = "Just now",
    val durationSeconds: Int = 0,
    val direction: CallDirection = CallDirection.INCOMING,
    val features: BehaviorFeatures = BehaviorFeatures(),
    val prediction: SpamPrediction,
    val matchedContext: ServiceContext? = null,
    val isTrusted: Boolean = false,
    val finalDecision: CallDecision = CallDecision.ALLOW,
    val userFeedback: UserFeedbackType? = null
) {
    val phoneNumberHash: String
        get() = Caller.hashNumber(phoneNumber)

    val displayTitle: String
        get() = callerName ?: phoneNumber
}

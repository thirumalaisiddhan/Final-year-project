package com.callguard.ai.data.model

import java.util.UUID

data class UserFeedback(
    val id: String = UUID.randomUUID().toString(),
    val callEventId: String,
    val phoneNumber: String,
    val feedback: UserFeedbackType,
    val trustedAfterFeedback: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

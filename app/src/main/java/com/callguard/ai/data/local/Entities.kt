package com.callguard.ai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.callguard.ai.data.model.CallDecision
import com.callguard.ai.data.model.CallDirection
import com.callguard.ai.data.model.RiskLevel
import com.callguard.ai.data.model.RiskThreshold
import com.callguard.ai.data.model.TrustedCategory
import com.callguard.ai.data.model.UserFeedbackType

@Entity(tableName = "call_events")
data class CallEventEntity(
    @PrimaryKey val id: String,
    val phoneNumber: String,
    val phoneNumberHash: String,
    val callerName: String?,
    val timestamp: Long,
    val timeFormatted: String,
    val durationSeconds: Int,
    val direction: CallDirection,
    val calls1Min: Int,
    val calls5Min: Int,
    val calls1Hour: Int,
    val averageDurationSeconds: Float,
    val shortCallRatio: Float,
    val repeatCallRatio: Float,
    val nightCallRatio: Float,
    val burstScore: Float,
    val riskScore: Int,
    val riskLevel: RiskLevel,
    val predictionReasons: List<String>,
    val modelVersion: String,
    val decisionSource: String,
    val matchedContextOrg: String?,
    val isTrusted: Boolean,
    val finalDecision: CallDecision,
    val userFeedback: UserFeedbackType?
)

@Entity(tableName = "trusted_callers")
data class TrustedCallerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phoneNumberOrIdentifier: String,
    val category: TrustedCategory,
    val reasonForTrust: String,
    val approvalDuration: String,
    val notes: String,
    val isEnabled: Boolean,
    val isOrganization: Boolean,
    val approvalStatus: String,
    val addedDate: String,
    val expiryDate: String?
)

@Entity(tableName = "service_context")
data class ServiceContextEntity(
    @PrimaryKey val id: String,
    val organizationName: String,
    val category: String,
    val status: String,
    val expectedDate: String,
    val deliveryCallsAllowed: Boolean,
    val isEnabled: Boolean,
    val notes: String
)

@Entity(tableName = "predictions")
data class SpamPredictionEntity(
    @PrimaryKey val id: String,
    val callEventId: String,
    val riskScore: Int,
    val riskLevel: RiskLevel,
    val reasons: List<String>,
    val modelVersion: String,
    val decisionSource: String,
    val timestamp: Long
)

@Entity(tableName = "user_feedback")
data class UserFeedbackEntity(
    @PrimaryKey val id: String,
    val callEventId: String,
    val phoneNumber: String,
    val feedback: UserFeedbackType,
    val trustedAfterFeedback: Boolean,
    val timestamp: Long
)

@Entity(tableName = "app_settings")
data class AppSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val protectionEnabled: Boolean,
    val autoBlockingEnabled: Boolean,
    val warnBeforeBlocking: Boolean,
    val trustedCallerOverride: Boolean,
    val deliveryContextEnabled: Boolean,
    val unknownCallerWarnings: Boolean,
    val riskThreshold: RiskThreshold,
    val isDarkMode: Boolean?
)

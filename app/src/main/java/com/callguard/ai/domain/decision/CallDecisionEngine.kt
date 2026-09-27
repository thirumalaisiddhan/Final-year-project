package com.callguard.ai.domain.decision

import com.callguard.ai.data.model.AppSettings
import com.callguard.ai.data.model.CallDecision
import com.callguard.ai.data.model.RiskLevel
import com.callguard.ai.data.model.ServiceContext
import com.callguard.ai.data.model.SpamPrediction

data class DecisionResult(
    val decision: CallDecision,
    val rationale: String,
    val actionRecommendation: String,
    val isDeliveryProtected: Boolean = false
)

/**
 * Intelligent Call Decision Engine.
 * Combines ML behavioral inference with trusted contacts, verified organizations,
 * real-time delivery/service context, and user security configurations.
 */
class CallDecisionEngine {

    fun evaluate(
        prediction: SpamPrediction,
        isTrusted: Boolean,
        isVerifiedOrg: Boolean,
        matchedContext: ServiceContext?,
        settings: AppSettings
    ): DecisionResult {
        // 1. Protection disabled check
        if (!settings.protectionEnabled) {
            return DecisionResult(
                decision = CallDecision.ALLOW,
                rationale = "Protection is currently switched OFF by user.",
                actionRecommendation = "Protection paused"
            )
        }

        // 2. Trusted caller override
        if (isTrusted && settings.trustedCallerOverride) {
            return DecisionResult(
                decision = CallDecision.ALLOW,
                rationale = "Caller or organization is in your Approved Trusted list.",
                actionRecommendation = "Allow Call (Trusted)"
            )
        }

        // 3. Verified organization check
        if (isVerifiedOrg) {
            return DecisionResult(
                decision = CallDecision.ALLOW,
                rationale = "Caller verified by legitimate organizational credentials.",
                actionRecommendation = "Verified Organization"
            )
        }

        // 4. Critical Delivery / Service Context Handling
        // Even if ML risk is HIGH (due to rapid unknown dials typical of delivery couriers),
        // we NEVER automatically block if there is an active delivery context!
        if (settings.deliveryContextEnabled && matchedContext != null && matchedContext.isEnabled) {
            return if (prediction.riskLevel == RiskLevel.HIGH) {
                DecisionResult(
                    decision = CallDecision.REVIEW,
                    rationale = "Active ${matchedContext.organizationName} delivery detected (${matchedContext.status}). Review before blocking.",
                    actionRecommendation = "Review Required — Active Delivery Context",
                    isDeliveryProtected = true
                )
            } else {
                DecisionResult(
                    decision = CallDecision.ALLOW,
                    rationale = "Matched active service context: ${matchedContext.organizationName}.",
                    actionRecommendation = "Allow Call (${matchedContext.organizationName})",
                    isDeliveryProtected = true
                )
            }
        }

        // 5. ML Behavioral Risk Rules
        return when (prediction.riskLevel) {
            RiskLevel.LOW -> {
                DecisionResult(
                    decision = CallDecision.ALLOW,
                    rationale = "Normal behavioral calling patterns. Low spam risk (${prediction.riskScore}%).",
                    actionRecommendation = "Allow Call"
                )
            }
            RiskLevel.MEDIUM -> {
                DecisionResult(
                    decision = CallDecision.WARN,
                    rationale = "Elevated burst or short-call activity (${prediction.riskScore}%). Exercise caution.",
                    actionRecommendation = "Warn User"
                )
            }
            RiskLevel.HIGH -> {
                if (settings.autoBlockingEnabled && !settings.warnBeforeBlocking) {
                    DecisionResult(
                        decision = CallDecision.BLOCK,
                        rationale = "High spam burst pattern with no active legitimate context. Automatically blocked.",
                        actionRecommendation = "Block Call"
                    )
                } else {
                    DecisionResult(
                        decision = CallDecision.WARN,
                        rationale = "High behavioral risk (${prediction.riskScore}%). Robocall or spam campaign detected.",
                        actionRecommendation = "Warn & Screen Call"
                    )
                }
            }
        }
    }
}

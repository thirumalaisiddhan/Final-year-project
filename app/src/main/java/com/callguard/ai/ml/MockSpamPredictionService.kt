package com.callguard.ai.ml

import com.callguard.ai.data.model.BehaviorFeatures
import com.callguard.ai.data.model.Caller
import com.callguard.ai.data.model.RiskLevel
import com.callguard.ai.data.model.SpamPrediction
import kotlin.math.min

/**
 * Deterministic Mock Spam Prediction implementation.
 * Produces realistic, transparent risk scores and telemetry explanations.
 */
class MockSpamPredictionService : SpamPredictionService {

    override suspend fun predict(features: BehaviorFeatures, caller: Caller): SpamPrediction {
        var score = 10 // Baseline ambient risk

        val reasons = mutableListOf<String>()

        if (caller.isUnknown) {
            score += 15
            reasons.add("Unknown caller (not in contacts)")
        }

        // Burst calling feature weights
        if (features.calls5Min >= 6) {
            score += 35
            reasons.add("High calling frequency (${features.calls5Min} calls in 5 min)")
        } else if (features.calls5Min >= 3) {
            score += 20
            reasons.add("Elevated calling frequency (${features.calls5Min} calls in 5 min)")
        }

        // Short call ratio (robocall pattern)
        if (features.shortCallRatio >= 0.70f) {
            score += 25
            reasons.add("Repeated short calls (${(features.shortCallRatio * 100).toInt()}%)")
        } else if (features.shortCallRatio >= 0.40f) {
            score += 15
            reasons.add("Above-average short call duration (${features.averageDurationSeconds}s)")
        }

        // Repeat call ratio
        if (features.repeatCallRatio >= 0.60f) {
            score += 15
            reasons.add("High repeat call pattern (${(features.repeatCallRatio * 100).toInt()}%)")
        }

        // Burst score
        if (features.burstScore >= 0.75f) {
            score += 15
            reasons.add("Burst pattern: rapid automated dialing rate")
        }

        // Night calling
        if (features.nightCallRatio >= 0.50f) {
            score += 10
            reasons.add("Abnormal night-time dialing window")
        }

        // Verified org mitigation
        if (caller.isVerifiedOrg) {
            score = (score * 0.35f).toInt()
            reasons.add("Verified organization cryptographic token matched")
        }

        val clampedScore = min(98, score.coerceAtLeast(5))
        val riskLevel = RiskLevel.fromScore(clampedScore)

        if (reasons.isEmpty()) {
            reasons.add("No anomalous behavioral features detected")
            reasons.add("Calling rate within regular human thresholds")
        }

        return SpamPrediction(
            riskScore = clampedScore,
            riskLevel = riskLevel,
            reasons = reasons,
            modelVersion = "v1.0-SBAS-XGBoost",
            decisionSource = "Behavioral + Telephony Telemetry"
        )
    }
}

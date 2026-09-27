package com.callguard.ai.data.model

/**
 * Output of SpamPredictionService.
 */
data class SpamPrediction(
    val riskScore: Int,                 // 0 to 100%
    val riskLevel: RiskLevel,
    val reasons: List<String>,
    val modelVersion: String = "v1.0-SBAS-XGBoost",
    val decisionSource: String = "Behavioral + Context"
)

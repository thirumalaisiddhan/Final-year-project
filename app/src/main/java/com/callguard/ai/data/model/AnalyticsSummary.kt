package com.callguard.ai.data.model

data class DailyActivityPoint(
    val day: String,
    val callsAnalyzed: Int,
    val spamBlocked: Int
)

data class RiskDistributionItem(
    val level: RiskLevel,
    val count: Int,
    val percentage: Float
)

data class AnalyticsSummary(
    val totalCallsAnalyzed: Int = 126,
    val suspiciousCalls: Int = 18,
    val falsePositives: Int = 3,
    val blockedCalls: Int = 9,
    val allowedCalls: Int = 110,
    val warningsCount: Int = 7,
    val dailyActivity: List<DailyActivityPoint> = emptyList(),
    val riskDistribution: List<RiskDistributionItem> = emptyList(),
    val deliveryContextSaves: Int = 5 // Legitimate deliveries saved from being blocked
)

package com.callguard.ai.data.model

/**
 * Behavioral features extracted from caller's telephony patterns.
 * In production, these are fed into SBAS feature selection & XGBoost classifier.
 */
data class BehaviorFeatures(
    val calls1Min: Int = 0,
    val calls5Min: Int = 0,
    val calls1Hour: Int = 0,
    val averageDurationSeconds: Float = 0f,
    val shortCallRatio: Float = 0f,    // 0.0 - 1.0 (calls < 10 seconds)
    val repeatCallRatio: Float = 0f,   // 0.0 - 1.0
    val nightCallRatio: Float = 0f,    // 0.0 - 1.0 (calls between 10PM - 6AM)
    val burstScore: Float = 0f         // Rate of change in calling frequency
) {
    val behavioralSummary: List<String>
        get() {
            val list = mutableListOf<String>()
            if (calls5Min >= 4) list.add("High calling frequency ($calls5Min calls in 5 min)")
            if (shortCallRatio >= 0.5f) list.add("Repeated short calls (${(shortCallRatio * 100).toInt()}%)")
            if (repeatCallRatio >= 0.6f) list.add("High repeat call pattern (${(repeatCallRatio * 100).toInt()}%)")
            if (burstScore >= 0.7f) list.add("Abnormal burst frequency detected")
            if (nightCallRatio >= 0.4f) list.add("Night-time activity detected")
            if (list.isEmpty()) list.add("Normal interaction profile")
            return list
        }
}

package com.callguard.ai.data.remote

import com.callguard.ai.data.model.AnalyticsSummary
import com.callguard.ai.data.model.BehaviorFeatures
import com.callguard.ai.data.model.CallEvent
import com.callguard.ai.data.model.SpamPrediction
import com.callguard.ai.data.model.TrustedCaller
import com.callguard.ai.data.model.UserFeedback

/**
 * REST API Service contract for future backend integration.
 * Enables the Android client to sync with a central server or cloud ML engine.
 */
interface CallGuardApiService {

    /**
     * POST /api/predictions
     * Request cloud-enhanced XGBoost model inference.
     */
    suspend fun requestPrediction(features: BehaviorFeatures, callerHash: String): Result<SpamPrediction>

    /**
     * GET /api/call-history
     * Sync call logs with secure remote audit vault.
     */
    suspend fun getCallHistory(): Result<List<CallEvent>>

    /**
     * GET /api/trusted-callers
     * Fetch organization-wide verified callers and delivery syndicates.
     */
    suspend fun getTrustedCallers(): Result<List<TrustedCaller>>

    /**
     * POST /api/trusted-callers
     * Register new approved caller or delivery whitelist token.
     */
    suspend fun registerTrustedCaller(caller: TrustedCaller): Result<Boolean>

    /**
     * POST /api/feedback
     * Submit confirmed spam / false positive telemetry for active model retraining.
     */
    suspend fun submitFeedback(feedback: UserFeedback): Result<Boolean>

    /**
     * GET /api/analytics
     * Fetch community spam trend analytics.
     */
    suspend fun getAnalytics(): Result<AnalyticsSummary>

    /**
     * GET /api/model/version
     * Check if updated SBAS feature weights or XGBoost model weights are available.
     */
    suspend fun getModelVersion(): Result<String>
}

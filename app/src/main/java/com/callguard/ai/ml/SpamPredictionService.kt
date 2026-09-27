package com.callguard.ai.ml

import com.callguard.ai.data.model.BehaviorFeatures
import com.callguard.ai.data.model.Caller
import com.callguard.ai.data.model.SpamPrediction

/**
 * Interface for Spam Prediction ML Service.
 * In future stages, this interface will invoke the trained XGBoost model via ONNX / TFLite.
 */
interface SpamPredictionService {
    /**
     * Compute risk score and classification based on behavioral features and caller telemetry.
     */
    suspend fun predict(features: BehaviorFeatures, caller: Caller): SpamPrediction
}

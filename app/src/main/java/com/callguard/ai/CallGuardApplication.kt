package com.callguard.ai

import android.app.Application
import com.callguard.ai.data.local.AppDatabase
import com.callguard.ai.data.repository.CallGuardRepository
import com.callguard.ai.domain.decision.CallDecisionEngine
import com.callguard.ai.ml.MockSpamPredictionService
import com.callguard.ai.ml.SpamPredictionService

class CallGuardApplication : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var repository: CallGuardRepository
        private set

    lateinit var predictionService: SpamPredictionService
        private set

    lateinit var decisionEngine: CallDecisionEngine
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        database = AppDatabase.getInstance(this)
        repository = CallGuardRepository(database)
        predictionService = MockSpamPredictionService()
        decisionEngine = CallDecisionEngine()
    }

    companion object {
        lateinit var instance: CallGuardApplication
            private set
    }
}

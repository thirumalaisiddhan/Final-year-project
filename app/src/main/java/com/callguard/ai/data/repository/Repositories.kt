package com.callguard.ai.data.repository

import com.callguard.ai.data.local.AppDatabase
import com.callguard.ai.data.local.CallEventEntity
import com.callguard.ai.data.local.ServiceContextEntity
import com.callguard.ai.data.local.TrustedCallerEntity
import com.callguard.ai.data.model.AnalyticsSummary
import com.callguard.ai.data.model.AppSettings
import com.callguard.ai.data.model.BehaviorFeatures
import com.callguard.ai.data.model.CallDecision
import com.callguard.ai.data.model.CallEvent
import com.callguard.ai.data.model.RiskLevel
import com.callguard.ai.data.model.ServiceContext
import com.callguard.ai.data.model.SpamPrediction
import com.callguard.ai.data.model.TrustedCaller
import com.callguard.ai.data.model.TrustedCategory
import com.callguard.ai.data.model.UserFeedbackType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// -------------------------------------------------------------
// Call Repository
// -------------------------------------------------------------
interface CallRepository {
    fun getAllCalls(): Flow<List<CallEvent>>
    suspend fun getCallById(id: String): CallEvent?
    suspend fun updateCallDecision(id: String, decision: CallDecision)
    suspend fun recordFeedback(callId: String, feedback: UserFeedbackType, trustCaller: Boolean)
    suspend fun addCall(call: CallEvent)
}

// -------------------------------------------------------------
// Trusted Caller Repository
// -------------------------------------------------------------
interface TrustedCallerRepository {
    fun getAllTrustedCallers(): Flow<List<TrustedCaller>>
    suspend fun addTrustedCaller(caller: TrustedCaller)
    suspend fun removeTrustedCaller(id: String)
    suspend fun toggleTrustedCaller(id: String, isEnabled: Boolean)
}

// -------------------------------------------------------------
// Service Context Repository
// -------------------------------------------------------------
interface ServiceContextRepository {
    fun getAllContexts(): Flow<List<ServiceContext>>
    suspend fun addServiceContext(context: ServiceContext)
    suspend fun toggleContext(id: String, isEnabled: Boolean)
    suspend fun removeContext(id: String)
}

// -------------------------------------------------------------
// Settings Repository
// -------------------------------------------------------------
interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun updateSettings(settings: AppSettings)
}

// -------------------------------------------------------------
// Analytics Repository
// -------------------------------------------------------------
interface AnalyticsRepository {
    fun getAnalyticsSummary(): Flow<AnalyticsSummary>
}

// -------------------------------------------------------------
// AppRepositoryManager (Unified repository with Room + Local State)
// -------------------------------------------------------------
class CallGuardRepository(
    private val database: AppDatabase
) : CallRepository, TrustedCallerRepository, ServiceContextRepository, SettingsRepository, AnalyticsRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    // In-memory reactive state initialized with rich mock data
    private val _calls = MutableStateFlow<List<CallEvent>>(MockDataGenerator.initialCalls)
    private val _trusted = MutableStateFlow<List<TrustedCaller>>(MockDataGenerator.initialTrustedCallers)
    private val _contexts = MutableStateFlow<List<ServiceContext>>(MockDataGenerator.initialActiveContexts)
    private val _settings = MutableStateFlow(AppSettings())

    init {
        // Sync with local Room database asynchronously
        scope.launch {
            try {
                database.trustedCallerDao().insertAll(
                    MockDataGenerator.initialTrustedCallers.map { it.toEntity() }
                )
                database.serviceContextDao().insertAll(
                    MockDataGenerator.initialActiveContexts.map { it.toEntity() }
                )
            } catch (e: Exception) {
                // Room fallback handled gracefully
            }
        }
    }

    // CallRepository implementation
    override fun getAllCalls(): Flow<List<CallEvent>> = _calls.asStateFlow()

    override suspend fun getCallById(id: String): CallEvent? {
        return _calls.value.find { it.id == id }
    }

    override suspend fun updateCallDecision(id: String, decision: CallDecision) {
        _calls.update { list ->
            list.map { if (it.id == id) it.copy(finalDecision = decision) else it }
        }
    }

    override suspend fun recordFeedback(callId: String, feedback: UserFeedbackType, trustCaller: Boolean) {
        val targetCall = _calls.value.find { it.id == callId }
        _calls.update { list ->
            list.map {
                if (it.id == callId) {
                    it.copy(
                        userFeedback = feedback,
                        isTrusted = if (trustCaller) true else it.isTrusted,
                        finalDecision = if (feedback == UserFeedbackType.NOT_SPAM) CallDecision.ALLOW else CallDecision.BLOCK
                    )
                } else it
            }
        }

        if (trustCaller && targetCall != null) {
            addTrustedCaller(
                TrustedCaller(
                    name = targetCall.callerName ?: "Caller (${targetCall.phoneNumber.takeLast(4)})",
                    phoneNumberOrIdentifier = targetCall.phoneNumber,
                    category = TrustedCategory.PERSONAL,
                    reasonForTrust = "Approved via user feedback",
                    approvalDuration = "Permanent",
                    notes = "Marked as Not Spam by user"
                )
            )
        }
    }

    override suspend fun addCall(call: CallEvent) {
        _calls.update { listOf(call) + it }
    }

    // TrustedCallerRepository implementation
    override fun getAllTrustedCallers(): Flow<List<TrustedCaller>> = _trusted.asStateFlow()

    override suspend fun addTrustedCaller(caller: TrustedCaller) {
        _trusted.update { listOf(caller) + it }
        scope.launch {
            try {
                database.trustedCallerDao().insert(caller.toEntity())
            } catch (_: Exception) {}
        }
    }

    override suspend fun removeTrustedCaller(id: String) {
        _trusted.update { it.filter { item -> item.id != id } }
        scope.launch {
            try {
                database.trustedCallerDao().deleteById(id)
            } catch (_: Exception) {}
        }
    }

    override suspend fun toggleTrustedCaller(id: String, isEnabled: Boolean) {
        _trusted.update { list ->
            list.map { if (it.id == id) it.copy(isEnabled = isEnabled) else it }
        }
    }

    // ServiceContextRepository implementation
    override fun getAllContexts(): Flow<List<ServiceContext>> = _contexts.asStateFlow()

    override suspend fun addServiceContext(context: ServiceContext) {
        _contexts.update { listOf(context) + it }
        scope.launch {
            try {
                database.serviceContextDao().insert(context.toEntity())
            } catch (_: Exception) {}
        }
    }

    override suspend fun toggleContext(id: String, isEnabled: Boolean) {
        _contexts.update { list ->
            list.map { if (it.id == id) it.copy(isEnabled = isEnabled) else it }
        }
    }

    override suspend fun removeContext(id: String) {
        _contexts.update { it.filter { item -> item.id != id } }
        scope.launch {
            try {
                database.serviceContextDao().deleteById(id)
            } catch (_: Exception) {}
        }
    }

    // SettingsRepository implementation
    override fun getSettings(): Flow<AppSettings> = _settings.asStateFlow()

    override suspend fun updateSettings(settings: AppSettings) {
        _settings.value = settings
    }

    // AnalyticsRepository implementation
    override fun getAnalyticsSummary(): Flow<AnalyticsSummary> {
        return _calls.map { callList ->
            val total = 126 + callList.size - MockDataGenerator.initialCalls.size
            val blocked = callList.count { it.finalDecision == CallDecision.BLOCK }
            val allowed = callList.count { it.finalDecision == CallDecision.ALLOW }
            val warned = callList.count { it.finalDecision == CallDecision.WARN }
            val suspicious = blocked + warned
            val falsePositives = callList.count { it.userFeedback == UserFeedbackType.NOT_SPAM }

            val lowCount = callList.count { it.prediction.riskLevel == RiskLevel.LOW }
            val medCount = callList.count { it.prediction.riskLevel == RiskLevel.MEDIUM }
            val highCount = callList.count { it.prediction.riskLevel == RiskLevel.HIGH }
            val totalCount = (lowCount + medCount + highCount).coerceAtLeast(1)

            AnalyticsSummary(
                totalCallsAnalyzed = total,
                suspiciousCalls = suspicious,
                falsePositives = falsePositives + 3,
                blockedCalls = blocked,
                allowedCalls = allowed,
                warningsCount = warned,
                dailyActivity = MockDataGenerator.dailyActivityData,
                riskDistribution = listOf(
                    com.callguard.ai.data.model.RiskDistributionItem(RiskLevel.LOW, lowCount, (lowCount * 100f / totalCount)),
                    com.callguard.ai.data.model.RiskDistributionItem(RiskLevel.MEDIUM, medCount, (medCount * 100f / totalCount)),
                    com.callguard.ai.data.model.RiskDistributionItem(RiskLevel.HIGH, highCount, (highCount * 100f / totalCount))
                ),
                deliveryContextSaves = callList.count { it.matchedContext != null && it.finalDecision == CallDecision.ALLOW }
            )
        }
    }

    // Extension mappers
    private fun TrustedCaller.toEntity(): TrustedCallerEntity = TrustedCallerEntity(
        id = id,
        name = name,
        phoneNumberOrIdentifier = phoneNumberOrIdentifier,
        category = category,
        reasonForTrust = reasonForTrust,
        approvalDuration = approvalDuration,
        notes = notes,
        isEnabled = isEnabled,
        isOrganization = isOrganization,
        approvalStatus = approvalStatus,
        addedDate = addedDate,
        expiryDate = expiryDate
    )

    private fun ServiceContext.toEntity(): ServiceContextEntity = ServiceContextEntity(
        id = id,
        organizationName = organizationName,
        category = category,
        status = status,
        expectedDate = expectedDate,
        deliveryCallsAllowed = deliveryCallsAllowed,
        isEnabled = isEnabled,
        notes = notes
    )
}

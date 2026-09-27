package com.callguard.ai.ui.screens.incoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callguard.ai.data.model.BehaviorFeatures
import com.callguard.ai.data.model.CallDecision
import com.callguard.ai.data.model.CallDirection
import com.callguard.ai.data.model.CallEvent
import com.callguard.ai.data.model.Caller
import com.callguard.ai.data.model.RiskLevel
import com.callguard.ai.data.model.ServiceContext
import com.callguard.ai.data.model.SpamPrediction
import com.callguard.ai.data.model.TrustedCaller
import com.callguard.ai.data.model.TrustedCategory
import com.callguard.ai.data.repository.CallGuardRepository
import com.callguard.ai.domain.decision.CallDecisionEngine
import com.callguard.ai.domain.decision.DecisionResult
import com.callguard.ai.ml.SpamPredictionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class IncomingCallScenario(
    val name: String,
    val caller: Caller,
    val features: BehaviorFeatures,
    val matchedContext: ServiceContext?,
    val isTrusted: Boolean
)

data class IncomingCallUiState(
    val currentScenarioIndex: Int = 0,
    val caller: Caller,
    val features: BehaviorFeatures,
    val prediction: SpamPrediction,
    val matchedContext: ServiceContext?,
    val isTrusted: Boolean,
    val decisionResult: DecisionResult,
    val callHandledMessage: String? = null
)

class IncomingCallViewModel(
    private val repository: CallGuardRepository,
    private val predictionService: SpamPredictionService,
    private val decisionEngine: CallDecisionEngine
) : ViewModel() {

    private val scenarios = listOf(
        // Scenario 0: Delivery Courier with High Burst Risk -> Context Protects!
        IncomingCallScenario(
            name = "Amazon Courier (High Risk + Active Context)",
            caller = Caller(phoneNumber = "+91 91234 56789", displayName = "Unknown Delivery Partner", isUnknown = true),
            features = BehaviorFeatures(calls1Min = 2, calls5Min = 6, calls1Hour = 18, shortCallRatio = 0.72f, repeatCallRatio = 0.60f, burstScore = 0.80f),
            matchedContext = ServiceContext(organizationName = "Amazon", category = "Delivery", status = "Out for Delivery", expectedDate = "Today", deliveryCallsAllowed = true),
            isTrusted = false
        ),
        // Scenario 1: Malicious Robocaller (High Risk, No Context) -> BLOCK
        IncomingCallScenario(
            name = "Robocall Burst (High Risk - Unknown)",
            caller = Caller(phoneNumber = "+91 98765 43210", displayName = null, isUnknown = true),
            features = BehaviorFeatures(calls1Min = 4, calls5Min = 10, calls1Hour = 38, averageDurationSeconds = 3.2f, shortCallRatio = 0.88f, repeatCallRatio = 0.80f, burstScore = 0.92f),
            matchedContext = null,
            isTrusted = false
        ),
        // Scenario 2: Telemarketing Promo (Medium Risk) -> WARN
        IncomingCallScenario(
            name = "Telemarketing Promo (Medium Risk)",
            caller = Caller(phoneNumber = "+91 87654 32109", displayName = "Promo Services", isUnknown = true),
            features = BehaviorFeatures(calls1Min = 1, calls5Min = 3, calls1Hour = 8, averageDurationSeconds = 16.5f, shortCallRatio = 0.44f, repeatCallRatio = 0.35f, burstScore = 0.45f),
            matchedContext = null,
            isTrusted = false
        ),
        // Scenario 3: College Admin Office (Low Risk / Approved) -> ALLOW
        IncomingCallScenario(
            name = "College Office (Low Risk - Verified)",
            caller = Caller(phoneNumber = "+91 98450 11223", displayName = "College Administration", isUnknown = false, organization = "University"),
            features = BehaviorFeatures(calls1Min = 0, calls5Min = 1, calls1Hour = 2, averageDurationSeconds = 90f, shortCallRatio = 0.05f),
            matchedContext = null,
            isTrusted = true
        )
    )

    private val _uiState = MutableStateFlow(buildStateForScenario(0))
    val uiState: StateFlow<IncomingCallUiState> = _uiState.asStateFlow()

    fun switchScenario(index: Int) {
        val safeIndex = index.coerceIn(0, scenarios.size - 1)
        _uiState.value = buildStateForScenario(safeIndex)
    }

    private fun buildStateForScenario(index: Int): IncomingCallUiState {
        val scenario = scenarios[index]
        val score = when (index) {
            0 -> 76
            1 -> 87
            2 -> 54
            else -> 14
        }
        val riskLevel = RiskLevel.fromScore(score)
        val prediction = SpamPrediction(
            riskScore = score,
            riskLevel = riskLevel,
            reasons = scenario.features.behavioralSummary + if (scenario.caller.isUnknown) listOf("Unknown caller (not in contacts)") else emptyList()
        )

        val decisionResult = decisionEngine.evaluate(
            prediction = prediction,
            isTrusted = scenario.isTrusted,
            isVerifiedOrg = scenario.caller.isVerifiedOrg,
            matchedContext = scenario.matchedContext,
            settings = com.callguard.ai.data.model.AppSettings()
        )

        return IncomingCallUiState(
            currentScenarioIndex = index,
            caller = scenario.caller,
            features = scenario.features,
            prediction = prediction,
            matchedContext = scenario.matchedContext,
            isTrusted = scenario.isTrusted,
            decisionResult = decisionResult
        )
    }

    fun makeDecision(decision: CallDecision) {
        val state = _uiState.value
        viewModelScope.launch {
            val event = CallEvent(
                phoneNumber = state.caller.phoneNumber,
                callerName = state.caller.displayName,
                features = state.features,
                prediction = state.prediction,
                matchedContext = state.matchedContext,
                isTrusted = state.isTrusted,
                finalDecision = decision,
                timeFormatted = "Just now"
            )
            repository.addCall(event)
            _uiState.value = state.copy(
                callHandledMessage = "Decision recorded: ${decision.label}"
            )
        }
    }

    fun trustCurrentCaller() {
        val state = _uiState.value
        viewModelScope.launch {
            val newTrusted = TrustedCaller(
                name = state.caller.displayName ?: "Caller (${state.caller.phoneNumber.takeLast(4)})",
                phoneNumberOrIdentifier = state.caller.phoneNumber,
                category = if (state.matchedContext != null) TrustedCategory.DELIVERY else TrustedCategory.PERSONAL,
                reasonForTrust = "Approved during incoming call screening",
                approvalDuration = "Permanent",
                notes = if (state.matchedContext != null) "Associated with ${state.matchedContext.organizationName}" else "Trusted user"
            )
            repository.addTrustedCaller(newTrusted)
            makeDecision(CallDecision.ALLOW)
            _uiState.value = _uiState.value.copy(
                callHandledMessage = "Caller added to Approved Trusted list!"
            )
        }
    }
}

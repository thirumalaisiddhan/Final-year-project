package com.callguard.ai.ui.screens.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callguard.ai.data.model.AnalyticsSummary
import com.callguard.ai.data.repository.CallGuardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AnalyticsViewModel(
    repository: CallGuardRepository
) : ViewModel() {

    val analytics: StateFlow<AnalyticsSummary> = repository.getAnalyticsSummary()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AnalyticsSummary())
}

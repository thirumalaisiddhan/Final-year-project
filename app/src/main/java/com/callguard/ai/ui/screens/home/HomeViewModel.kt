package com.callguard.ai.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callguard.ai.data.model.AnalyticsSummary
import com.callguard.ai.data.model.AppSettings
import com.callguard.ai.data.model.CallEvent
import com.callguard.ai.data.repository.CallGuardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val settings: AppSettings = AppSettings(),
    val analytics: AnalyticsSummary = AnalyticsSummary(),
    val recentCalls: List<CallEvent> = emptyList(),
    val greeting: String = "Good afternoon"
)

class HomeViewModel(
    private val repository: CallGuardRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = repository.getSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettings())

    val analytics: StateFlow<AnalyticsSummary> = repository.getAnalyticsSummary()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AnalyticsSummary())

    val recentCalls: StateFlow<List<CallEvent>> = repository.getAllCalls()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleProtection(enabled: Boolean) {
        viewModelScope.launch {
            val current = settings.value
            repository.updateSettings(current.copy(protectionEnabled = enabled))
        }
    }
}

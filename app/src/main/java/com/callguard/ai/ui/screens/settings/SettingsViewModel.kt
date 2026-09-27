package com.callguard.ai.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callguard.ai.data.model.AppSettings
import com.callguard.ai.data.model.RiskThreshold
import com.callguard.ai.data.repository.CallGuardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: CallGuardRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = repository.getSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettings())

    fun updateSettings(transform: (AppSettings) -> AppSettings) {
        viewModelScope.launch {
            val updated = transform(settings.value)
            repository.updateSettings(updated)
        }
    }

    fun setRiskThreshold(threshold: RiskThreshold) {
        updateSettings { it.copy(riskThreshold = threshold) }
    }
}

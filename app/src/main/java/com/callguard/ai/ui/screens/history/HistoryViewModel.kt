package com.callguard.ai.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callguard.ai.data.model.CallDecision
import com.callguard.ai.data.model.CallEvent
import com.callguard.ai.data.repository.CallGuardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

enum class HistoryFilter(val label: String) {
    ALL("All"),
    ALLOWED("Allowed"),
    WARNED("Warned"),
    BLOCKED("Blocked"),
    TRUSTED("Trusted")
}

class HistoryViewModel(
    private val repository: CallGuardRepository
) : ViewModel() {

    val selectedFilter = MutableStateFlow(HistoryFilter.ALL)

    val filteredCalls: StateFlow<List<CallEvent>> = combine(
        repository.getAllCalls(),
        selectedFilter
    ) { calls, filter ->
        when (filter) {
            HistoryFilter.ALL -> calls
            HistoryFilter.ALLOWED -> calls.filter { it.finalDecision == CallDecision.ALLOW }
            HistoryFilter.WARNED -> calls.filter { it.finalDecision == CallDecision.WARN }
            HistoryFilter.BLOCKED -> calls.filter { it.finalDecision == CallDecision.BLOCK }
            HistoryFilter.TRUSTED -> calls.filter { it.isTrusted }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectFilter(filter: HistoryFilter) {
        selectedFilter.value = filter
    }
}

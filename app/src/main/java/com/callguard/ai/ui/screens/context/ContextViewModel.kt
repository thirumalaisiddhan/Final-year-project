package com.callguard.ai.ui.screens.context

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callguard.ai.data.model.ServiceContext
import com.callguard.ai.data.repository.CallGuardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContextViewModel(
    private val repository: CallGuardRepository
) : ViewModel() {

    val contexts: StateFlow<List<ServiceContext>> = repository.getAllContexts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleContext(id: String, isEnabled: Boolean) {
        viewModelScope.launch {
            repository.toggleContext(id, isEnabled)
        }
    }

    fun removeContext(id: String) {
        viewModelScope.launch {
            repository.removeContext(id)
        }
    }

    fun addServiceContext(
        organizationName: String,
        category: String,
        status: String,
        expectedDate: String,
        notes: String
    ) {
        if (organizationName.isBlank()) return
        viewModelScope.launch {
            val context = ServiceContext(
                organizationName = organizationName.trim(),
                category = category,
                status = status,
                expectedDate = expectedDate,
                deliveryCallsAllowed = true,
                isEnabled = true,
                notes = notes.trim()
            )
            repository.addServiceContext(context)
        }
    }
}

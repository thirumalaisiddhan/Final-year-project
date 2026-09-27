package com.callguard.ai.ui.screens.trusted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callguard.ai.data.model.TrustedCaller
import com.callguard.ai.data.model.TrustedCategory
import com.callguard.ai.data.repository.CallGuardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrustedViewModel(
    private val repository: CallGuardRepository
) : ViewModel() {

    val trustedList: StateFlow<List<TrustedCaller>> = repository.getAllTrustedCallers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun toggleTrusted(id: String, isEnabled: Boolean) {
        viewModelScope.launch {
            repository.toggleTrustedCaller(id, isEnabled)
        }
    }

    fun removeTrusted(id: String) {
        viewModelScope.launch {
            repository.removeTrustedCaller(id)
        }
    }

    fun addTrustedCaller(
        name: String,
        numberOrId: String,
        category: TrustedCategory,
        reason: String,
        duration: String,
        notes: String
    ): Boolean {
        if (name.isBlank() || numberOrId.isBlank()) return false

        viewModelScope.launch {
            val newEntry = TrustedCaller(
                name = name.trim(),
                phoneNumberOrIdentifier = numberOrId.trim(),
                category = category,
                reasonForTrust = reason.trim(),
                approvalDuration = duration,
                notes = notes.trim(),
                isEnabled = true,
                isOrganization = category == TrustedCategory.DELIVERY || category == TrustedCategory.FINANCE || category == TrustedCategory.EDUCATION,
                addedDate = "Today"
            )
            repository.addTrustedCaller(newEntry)
            _saveSuccess.value = true
        }
        return true
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }
}

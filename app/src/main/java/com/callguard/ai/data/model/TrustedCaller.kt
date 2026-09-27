package com.callguard.ai.data.model

import java.util.UUID

/**
 * Trusted caller or organization record.
 * Supports individual phone numbers as well as organizational delivery/service contexts.
 */
data class TrustedCaller(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val phoneNumberOrIdentifier: String,
    val category: TrustedCategory = TrustedCategory.PERSONAL,
    val reasonForTrust: String = "",
    val approvalDuration: String = "Permanent", // e.g. "Permanent", "30 Days", "7 Days", "Today Only"
    val notes: String = "",
    val isEnabled: Boolean = true,
    val isOrganization: Boolean = false,
    val approvalStatus: String = "Approved",
    val addedDate: String = "Today",
    val expiryDate: String? = null
)

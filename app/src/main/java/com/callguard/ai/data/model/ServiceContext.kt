package com.callguard.ai.data.model

import java.util.UUID

/**
 * Real-time context representing active delivery or service providers.
 * Legitimate delivery numbers frequently change and appear as unknown/burst callers;
 * active context allows the intelligent decision engine to warn/review rather than blindly block.
 */
data class ServiceContext(
    val id: String = UUID.randomUUID().toString(),
    val organizationName: String,
    val category: String, // "Delivery", "E-Commerce", "Finance", "Home Service"
    val status: String,   // "Out for Delivery", "Arriving Today", "Active Account"
    val expectedDate: String,
    val deliveryCallsAllowed: Boolean = true,
    val isEnabled: Boolean = true,
    val notes: String = ""
)

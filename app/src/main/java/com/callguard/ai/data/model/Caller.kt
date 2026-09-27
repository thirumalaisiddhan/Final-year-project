package com.callguard.ai.data.model

import java.security.MessageDigest

/**
 * Caller representation with privacy-preserving hash support.
 */
data class Caller(
    val phoneNumber: String,
    val displayName: String? = null,
    val isUnknown: Boolean = false,
    val organization: String? = null,
    val isVerifiedOrg: Boolean = false
) {
    /**
     * Compute SHA-256 hash of the phone number to avoid plain text logging in production.
     */
    val phoneNumberHash: String
        get() = hashNumber(phoneNumber)

    val maskedNumber: String
        get() {
            val clean = phoneNumber.trim()
            return if (clean.length > 6) {
                clean.substring(0, 3) + " ••••• " + clean.substring(clean.length - 2)
            } else {
                "••••••"
            }
        }

    companion object {
        fun hashNumber(number: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(number.trim().toByteArray(Charsets.UTF_8))
            return hash.joinToString("") { "%02x".format(it) }.take(16)
        }
    }
}

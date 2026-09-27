package com.callguard.ai.data.repository

import com.callguard.ai.data.model.BehaviorFeatures
import com.callguard.ai.data.model.CallDecision
import com.callguard.ai.data.model.CallDirection
import com.callguard.ai.data.model.CallEvent
import com.callguard.ai.data.model.DailyActivityPoint
import com.callguard.ai.data.model.RiskDistributionItem
import com.callguard.ai.data.model.RiskLevel
import com.callguard.ai.data.model.ServiceContext
import com.callguard.ai.data.model.SpamPrediction
import com.callguard.ai.data.model.TrustedCaller
import com.callguard.ai.data.model.TrustedCategory
import com.callguard.ai.data.model.UserFeedbackType

object MockDataGenerator {

    val initialActiveContexts = listOf(
        ServiceContext(
            id = "ctx_amazon",
            organizationName = "Amazon",
            category = "Delivery",
            status = "Out for Delivery",
            expectedDate = "Today, by 4:00 PM",
            deliveryCallsAllowed = true,
            isEnabled = true,
            notes = "Package with Courier Delivery Associate"
        ),
        ServiceContext(
            id = "ctx_flipkart",
            organizationName = "Flipkart",
            category = "Delivery",
            status = "Arriving Today",
            expectedDate = "Today, by 7:30 PM",
            deliveryCallsAllowed = true,
            isEnabled = true,
            notes = "Electronics shipment out for delivery"
        ),
        ServiceContext(
            id = "ctx_emi",
            organizationName = "EMI Finance Company",
            category = "Finance",
            status = "Active Account",
            expectedDate = "Ongoing / Verified",
            deliveryCallsAllowed = true,
            isEnabled = true,
            notes = "Loan and installment servicing account"
        )
    )

    val initialTrustedCallers = listOf(
        TrustedCaller(
            id = "tc_amazon",
            name = "Amazon Delivery",
            phoneNumberOrIdentifier = "+91 91234 56789",
            category = TrustedCategory.DELIVERY,
            reasonForTrust = "Official Amazon logistics network",
            approvalDuration = "Permanent",
            notes = "Supports dynamic delivery partner numbers",
            isEnabled = true,
            isOrganization = true,
            approvalStatus = "Approved",
            addedDate = "Yesterday"
        ),
        TrustedCaller(
            id = "tc_college",
            name = "College Administration",
            phoneNumberOrIdentifier = "+91 98450 11223",
            category = TrustedCategory.EDUCATION,
            reasonForTrust = "University academic exams & placement cell",
            approvalDuration = "Permanent",
            notes = "Important academic alerts",
            isEnabled = true,
            isOrganization = true,
            approvalStatus = "Approved",
            addedDate = "Sep 12, 2026"
        ),
        TrustedCaller(
            id = "tc_emi",
            name = "EMI Finance Company",
            phoneNumberOrIdentifier = "+91 80491 55667",
            category = TrustedCategory.FINANCE,
            reasonForTrust = "Authorized auto-debit confirmation",
            approvalDuration = "30 Days",
            notes = "Verified NBFC partner",
            isEnabled = true,
            isOrganization = true,
            approvalStatus = "Approved",
            addedDate = "Sep 20, 2026"
        ),
        TrustedCaller(
            id = "tc_family",
            name = "Family - Home",
            phoneNumberOrIdentifier = "+91 98220 99887",
            category = TrustedCategory.PERSONAL,
            reasonForTrust = "Immediate family member",
            approvalDuration = "Permanent",
            notes = "Always allow without screening",
            isEnabled = true,
            isOrganization = false,
            approvalStatus = "Approved",
            addedDate = "Aug 01, 2026"
        ),
        TrustedCaller(
            id = "tc_service",
            name = "Service Technician",
            phoneNumberOrIdentifier = "+91 94433 22110",
            category = TrustedCategory.SERVICE,
            reasonForTrust = "Home AC maintenance engineer",
            approvalDuration = "7 Days",
            notes = "Urban Company scheduled service",
            isEnabled = true,
            isOrganization = false,
            approvalStatus = "Approved",
            addedDate = "Sep 25, 2026"
        )
    )

    val initialCalls: List<CallEvent> = listOf(
        CallEvent(
            id = "call_1",
            phoneNumber = "+91 98765 43210",
            callerName = "Unknown Caller",
            timestamp = System.currentTimeMillis() - 15 * 60 * 1000, // 15 mins ago
            timeFormatted = "10:42 AM",
            durationSeconds = 0,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 3,
                calls5Min = 8,
                calls1Hour = 31,
                averageDurationSeconds = 4.2f,
                shortCallRatio = 0.82f,
                repeatCallRatio = 0.74f,
                nightCallRatio = 0.1f,
                burstScore = 0.85f
            ),
            prediction = SpamPrediction(
                riskScore = 87,
                riskLevel = RiskLevel.HIGH,
                reasons = listOf(
                    "High calling frequency (8 calls in 5 min)",
                    "Repeated short calls (82%)",
                    "Unknown caller (not in contacts)",
                    "No previous interaction recorded"
                )
            ),
            matchedContext = null,
            isTrusted = false,
            finalDecision = CallDecision.BLOCK
        ),
        CallEvent(
            id = "call_2",
            phoneNumber = "+91 91234 56789",
            callerName = "Amazon Delivery",
            timestamp = System.currentTimeMillis() - 45 * 60 * 1000,
            timeFormatted = "11:15 AM",
            durationSeconds = 42,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 1,
                calls5Min = 4,
                calls1Hour = 14,
                averageDurationSeconds = 12.0f,
                shortCallRatio = 0.65f,
                repeatCallRatio = 0.50f,
                burstScore = 0.60f
            ),
            prediction = SpamPrediction(
                riskScore = 76,
                riskLevel = RiskLevel.HIGH,
                reasons = listOf(
                    "High calling burst pattern across region",
                    "Short interaction history",
                    "Rapid dialing sequence"
                )
            ),
            matchedContext = initialActiveContexts[0], // Amazon
            isTrusted = true,
            finalDecision = CallDecision.ALLOW // Critical safety logic: Delivery protected!
        ),
        CallEvent(
            id = "call_3",
            phoneNumber = "+91 87654 32109",
            callerName = "Unknown Caller",
            timestamp = System.currentTimeMillis() - 90 * 60 * 1000,
            timeFormatted = "12:30 PM",
            durationSeconds = 8,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 1,
                calls5Min = 3,
                calls1Hour = 9,
                averageDurationSeconds = 18.4f,
                shortCallRatio = 0.42f,
                repeatCallRatio = 0.35f,
                burstScore = 0.45f
            ),
            prediction = SpamPrediction(
                riskScore = 54,
                riskLevel = RiskLevel.MEDIUM,
                reasons = listOf(
                    "Elevated calling volume (3 calls in 5 min)",
                    "Unrecognized number with telemarketing flags"
                )
            ),
            matchedContext = null,
            isTrusted = false,
            finalDecision = CallDecision.WARN
        ),
        CallEvent(
            id = "call_4",
            phoneNumber = "+91 98450 11223",
            callerName = "College Administration",
            timestamp = System.currentTimeMillis() - 180 * 60 * 1000,
            timeFormatted = "09:15 AM",
            durationSeconds = 120,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 0,
                calls5Min = 1,
                calls1Hour = 2,
                averageDurationSeconds = 85f,
                shortCallRatio = 0.05f,
                repeatCallRatio = 0.10f
            ),
            prediction = SpamPrediction(
                riskScore = 12,
                riskLevel = RiskLevel.LOW,
                reasons = listOf(
                    "Approved educational organization",
                    "Normal human conversational duration"
                )
            ),
            matchedContext = null,
            isTrusted = true,
            finalDecision = CallDecision.ALLOW
        ),
        CallEvent(
            id = "call_5",
            phoneNumber = "+91 80491 55667",
            callerName = "EMI Finance Company",
            timestamp = System.currentTimeMillis() - 86400000L + 7200000L,
            timeFormatted = "Yesterday, 04:20 PM",
            durationSeconds = 65,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 0,
                calls5Min = 1,
                calls1Hour = 3,
                averageDurationSeconds = 54f,
                shortCallRatio = 0.15f,
                repeatCallRatio = 0.20f
            ),
            prediction = SpamPrediction(
                riskScore = 28,
                riskLevel = RiskLevel.LOW,
                reasons = listOf(
                    "Verified financial institution partner",
                    "Legitimate active account verification"
                )
            ),
            matchedContext = initialActiveContexts[2],
            isTrusted = true,
            finalDecision = CallDecision.ALLOW
        ),
        CallEvent(
            id = "call_6",
            phoneNumber = "+91 90000 12345",
            callerName = "Robocall Dialer",
            timestamp = System.currentTimeMillis() - 86400000L - 3600000L,
            timeFormatted = "Yesterday, 02:10 PM",
            durationSeconds = 0,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 4,
                calls5Min = 12,
                calls1Hour = 48,
                averageDurationSeconds = 2.1f,
                shortCallRatio = 0.95f,
                repeatCallRatio = 0.88f,
                burstScore = 0.96f
            ),
            prediction = SpamPrediction(
                riskScore = 95,
                riskLevel = RiskLevel.HIGH,
                reasons = listOf(
                    "Severe automated dialing burst (12 calls/5min)",
                    "Extreme short-call ratio (95%)",
                    "Telephony spoof signature pattern"
                )
            ),
            matchedContext = null,
            isTrusted = false,
            finalDecision = CallDecision.BLOCK,
            userFeedback = UserFeedbackType.CONFIRMED_SPAM
        ),
        CallEvent(
            id = "call_7",
            phoneNumber = "+91 99887 76655",
            callerName = "Flipkart Delivery Hub",
            timestamp = System.currentTimeMillis() - 2 * 86400000L,
            timeFormatted = "2 days ago, 01:45 PM",
            durationSeconds = 35,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 1,
                calls5Min = 3,
                calls1Hour = 11,
                averageDurationSeconds = 15f,
                shortCallRatio = 0.55f,
                repeatCallRatio = 0.40f,
                burstScore = 0.52f
            ),
            prediction = SpamPrediction(
                riskScore = 68,
                riskLevel = RiskLevel.MEDIUM,
                reasons = listOf(
                    "Rapid localized delivery dispatch pattern",
                    "Active Flipkart delivery context resolved"
                )
            ),
            matchedContext = initialActiveContexts[1],
            isTrusted = true,
            finalDecision = CallDecision.ALLOW
        ),
        CallEvent(
            id = "call_8",
            phoneNumber = "+91 98220 99887",
            callerName = "Family - Home",
            timestamp = System.currentTimeMillis() - 2 * 86400000L - 10000000L,
            timeFormatted = "2 days ago, 11:00 AM",
            durationSeconds = 240,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 0,
                calls5Min = 1,
                calls1Hour = 1,
                averageDurationSeconds = 180f,
                shortCallRatio = 0.0f,
                repeatCallRatio = 0.05f
            ),
            prediction = SpamPrediction(
                riskScore = 5,
                riskLevel = RiskLevel.LOW,
                reasons = listOf(
                    "Known personal contact",
                    "Zero spam indicators"
                )
            ),
            matchedContext = null,
            isTrusted = true,
            finalDecision = CallDecision.ALLOW
        ),
        CallEvent(
            id = "call_9",
            phoneNumber = "+91 94433 22110",
            callerName = "Service Technician",
            timestamp = System.currentTimeMillis() - 3 * 86400000L,
            timeFormatted = "3 days ago, 03:30 PM",
            durationSeconds = 48,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 0,
                calls5Min = 2,
                calls1Hour = 5,
                averageDurationSeconds = 32f,
                shortCallRatio = 0.30f,
                repeatCallRatio = 0.25f
            ),
            prediction = SpamPrediction(
                riskScore = 34,
                riskLevel = RiskLevel.LOW,
                reasons = listOf(
                    "Urban Company service booking verified",
                    "Legitimate technician coordination"
                )
            ),
            matchedContext = null,
            isTrusted = true,
            finalDecision = CallDecision.ALLOW
        ),
        CallEvent(
            id = "call_10",
            phoneNumber = "+91 93111 88990",
            callerName = "Investment Scam Bot",
            timestamp = System.currentTimeMillis() - 4 * 86400000L,
            timeFormatted = "4 days ago, 10:05 AM",
            durationSeconds = 0,
            direction = CallDirection.INCOMING,
            features = BehaviorFeatures(
                calls1Min = 3,
                calls5Min = 9,
                calls1Hour = 37,
                averageDurationSeconds = 3.5f,
                shortCallRatio = 0.89f,
                repeatCallRatio = 0.81f,
                burstScore = 0.92f
            ),
            prediction = SpamPrediction(
                riskScore = 91,
                riskLevel = RiskLevel.HIGH,
                reasons = listOf(
                    "Unregistered high-volume financial promo",
                    "Mass dialer fingerprint matched",
                    "Repeated unanswered call burst"
                )
            ),
            matchedContext = null,
            isTrusted = false,
            finalDecision = CallDecision.BLOCK,
            userFeedback = UserFeedbackType.CONFIRMED_SPAM
        )
    )

    val dailyActivityData = listOf(
        DailyActivityPoint("Mon", 18, 2),
        DailyActivityPoint("Tue", 24, 3),
        DailyActivityPoint("Wed", 19, 1),
        DailyActivityPoint("Thu", 27, 4),
        DailyActivityPoint("Fri", 32, 5),
        DailyActivityPoint("Sat", 21, 2),
        DailyActivityPoint("Sun", 15, 1)
    )

    val riskDistributionData = listOf(
        RiskDistributionItem(RiskLevel.LOW, 110, 87.3f),
        RiskDistributionItem(RiskLevel.MEDIUM, 7, 5.5f),
        RiskDistributionItem(RiskLevel.HIGH, 9, 7.2f)
    )
}

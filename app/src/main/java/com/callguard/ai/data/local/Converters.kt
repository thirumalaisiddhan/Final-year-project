package com.callguard.ai.data.local

import androidx.room.TypeConverter
import com.callguard.ai.data.model.CallDecision
import com.callguard.ai.data.model.CallDirection
import com.callguard.ai.data.model.RiskLevel
import com.callguard.ai.data.model.RiskThreshold
import com.callguard.ai.data.model.TrustedCategory
import com.callguard.ai.data.model.UserFeedbackType

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return value?.joinToString(";;;") ?: ""
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        return if (value.isNullOrBlank()) emptyList() else value.split(";;;")
    }

    @TypeConverter
    fun fromRiskLevel(level: RiskLevel): String = level.name

    @TypeConverter
    fun toRiskLevel(value: String): RiskLevel = runCatching { RiskLevel.valueOf(value) }.getOrDefault(RiskLevel.LOW)

    @TypeConverter
    fun fromCallDecision(decision: CallDecision): String = decision.name

    @TypeConverter
    fun toCallDecision(value: String): CallDecision = runCatching { CallDecision.valueOf(value) }.getOrDefault(CallDecision.ALLOW)

    @TypeConverter
    fun fromCallDirection(direction: CallDirection): String = direction.name

    @TypeConverter
    fun toCallDirection(value: String): CallDirection = runCatching { CallDirection.valueOf(value) }.getOrDefault(CallDirection.INCOMING)

    @TypeConverter
    fun fromTrustedCategory(cat: TrustedCategory): String = cat.name

    @TypeConverter
    fun toTrustedCategory(value: String): TrustedCategory = runCatching { TrustedCategory.valueOf(value) }.getOrDefault(TrustedCategory.PERSONAL)

    @TypeConverter
    fun fromUserFeedbackType(type: UserFeedbackType?): String? = type?.name

    @TypeConverter
    fun toUserFeedbackType(value: String?): UserFeedbackType? = value?.let {
        runCatching { UserFeedbackType.valueOf(it) }.getOrNull()
    }

    @TypeConverter
    fun fromRiskThreshold(threshold: RiskThreshold): String = threshold.name

    @TypeConverter
    fun toRiskThreshold(value: String): RiskThreshold = runCatching { RiskThreshold.valueOf(value) }.getOrDefault(RiskThreshold.HIGH)
}

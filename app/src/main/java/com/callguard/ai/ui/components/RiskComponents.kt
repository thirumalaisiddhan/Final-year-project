package com.callguard.ai.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Dangerous
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callguard.ai.data.model.RiskLevel

/**
 * Reusable, central RiskLevel component required by project specification.
 * Renders consistent semantic iconography, colors, and styling across screens.
 */
@Composable
fun RiskLevelBadge(
    level: RiskLevel,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true
) {
    val icon = when (level) {
        RiskLevel.LOW -> Icons.Rounded.CheckCircle
        RiskLevel.MEDIUM -> Icons.Rounded.Warning
        RiskLevel.HIGH -> Icons.Rounded.Dangerous
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(level.containerColor)
            .border(1.dp, level.badgeColor.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showIcon) {
            Icon(
                imageVector = icon,
                contentDescription = level.title,
                tint = level.badgeColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = level.title,
            color = level.badgeColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Circular Risk Score Gauge with animated sweep and center percentage.
 */
@Composable
fun RiskScoreGauge(
    score: Int,
    level: RiskLevel,
    modifier: Modifier = Modifier,
    size: Int = 110
) {
    val animatedProgress by animateFloatAsState(
        targetValue = score / 100f,
        animationSpec = tween(durationMillis = 1000),
        label = "riskProgress"
    )

    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        // Track background
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(size.dp),
            color = level.containerColor,
            strokeWidth = 10.dp,
            trackColor = Color.Transparent
        )

        // Animated progress
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.size(size.dp),
            color = level.badgeColor,
            strokeWidth = 10.dp,
            trackColor = Color.Transparent
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$score%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = level.badgeColor
            )
            Text(
                text = "RISK",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 9.sp,
                letterSpacing = 1.sp
            )
        }
    }
}

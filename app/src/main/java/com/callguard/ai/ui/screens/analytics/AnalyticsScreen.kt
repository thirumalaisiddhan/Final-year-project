package com.callguard.ai.ui.screens.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Feedback
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.automirrored.rounded.PhoneCallback
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callguard.ai.ui.components.DailyActivityBarChart
import com.callguard.ai.ui.components.RiskDistributionStackedBar
import com.callguard.ai.ui.components.StatCard
import com.callguard.ai.ui.theme.ContextBadge
import com.callguard.ai.ui.theme.RiskCritical
import com.callguard.ai.ui.theme.RiskSafe
import com.callguard.ai.ui.theme.RiskWarning

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel
) {
    val analytics by viewModel.analytics.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Analytics & Telemetry",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 6-metric cards grid
            Text(
                text = "Key Metrics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    title = "Calls Analyzed",
                    value = "${analytics.totalCallsAnalyzed}",
                    icon = Icons.AutoMirrored.Rounded.PhoneCallback,
                    iconColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Suspicious Calls",
                    value = "${analytics.suspiciousCalls}",
                    icon = Icons.Rounded.Warning,
                    iconColor = RiskWarning,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    title = "Blocked Calls",
                    value = "${analytics.blockedCalls}",
                    icon = Icons.Rounded.Block,
                    iconColor = RiskCritical,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Allowed Calls",
                    value = "${analytics.allowedCalls}",
                    icon = Icons.Rounded.CheckCircle,
                    iconColor = RiskSafe,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    title = "False Positives",
                    value = "${analytics.falsePositives}",
                    icon = Icons.Rounded.Feedback,
                    iconColor = MaterialTheme.colorScheme.primary,
                    subtitle = "Corrected via feedback",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Delivery Saves",
                    value = "${analytics.deliveryContextSaves}",
                    icon = Icons.Rounded.LocalShipping,
                    iconColor = ContextBadge,
                    subtitle = "Protected by context",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Blocked vs Allowed Visual Progress Card
            val totalDecided = (analytics.blockedCalls + analytics.allowedCalls).coerceAtLeast(1)
            val allowedFraction = analytics.allowedCalls.toFloat() / totalDecided

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Allowed vs Blocked Ratio",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${(allowedFraction * 100).toInt()}% Allowed",
                            style = MaterialTheme.typography.labelMedium,
                            color = RiskSafe,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { allowedFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = RiskSafe,
                        trackColor = RiskCritical
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${analytics.allowedCalls} Legitimate Calls Allowed",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${analytics.blockedCalls} Spam Blocked",
                            style = MaterialTheme.typography.labelSmall,
                            color = RiskCritical
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weekly Telemetry Bar Chart
            DailyActivityBarChart(data = analytics.dailyActivity)

            Spacer(modifier = Modifier.height(16.dp))

            // Risk Distribution Stacked Bar
            RiskDistributionStackedBar(distribution = analytics.riskDistribution)

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

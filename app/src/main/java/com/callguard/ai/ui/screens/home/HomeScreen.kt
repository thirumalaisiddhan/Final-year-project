package com.callguard.ai.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.automirrored.rounded.PhoneCallback
import androidx.compose.material.icons.rounded.PhoneInTalk
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callguard.ai.data.model.CallEvent
import com.callguard.ai.ui.components.CallItemRow
import com.callguard.ai.ui.components.StatCard
import com.callguard.ai.ui.theme.ContextBadge
import com.callguard.ai.ui.theme.RiskCritical
import com.callguard.ai.ui.theme.RiskSafe
import com.callguard.ai.ui.theme.RiskWarning

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToHistory: () -> Unit,
    onNavigateToTrusted: () -> Unit,
    onNavigateToContext: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToIncomingCall: () -> Unit,
    onNavigateToCallDetail: (String) -> Unit
) {
    val settings by viewModel.settings.collectAsState()
    val analytics by viewModel.analytics.collectAsState()
    val calls by viewModel.recentCalls.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Greeting & Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Good afternoon",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = if (settings.protectionEnabled) "Your phone is protected" else "Call protection is paused",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (settings.protectionEnabled) RiskSafe else RiskCritical
                    )
                }

                // Interactive Simulator shortcut button for project guide presentation
                Button(
                    onClick = onNavigateToIncomingCall,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PhoneInTalk,
                        contentDescription = "Simulate",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Test Screening", style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Protection Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (settings.protectionEnabled)
                        MaterialTheme.colorScheme.surfaceVariant
                    else
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (settings.protectionEnabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(
                                    if (settings.protectionEnabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                    else RiskCritical.copy(alpha = 0.15f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (settings.protectionEnabled) Icons.Rounded.Shield else Icons.Rounded.Block,
                                contentDescription = null,
                                tint = if (settings.protectionEnabled) MaterialTheme.colorScheme.primary else RiskCritical,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = if (settings.protectionEnabled) "Protection ON" else "Protection OFF",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (settings.protectionEnabled) "Real-time context screening active" else "Tap toggle to reactivate",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Switch(
                        checked = settings.protectionEnabled,
                        onCheckedChange = { viewModel.toggleProtection(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Statistics 2x2 Grid
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "Calls Analyzed",
                    value = "${analytics.totalCallsAnalyzed}",
                    icon = Icons.AutoMirrored.Rounded.PhoneCallback,
                    iconColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Suspicious",
                    value = "${analytics.suspiciousCalls}",
                    icon = Icons.Rounded.Warning,
                    iconColor = RiskWarning,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "Blocked",
                    value = "${analytics.blockedCalls}",
                    icon = Icons.Rounded.Block,
                    iconColor = RiskCritical,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Allowed",
                    value = "${analytics.allowedCalls}",
                    icon = Icons.Rounded.CheckCircle,
                    iconColor = RiskSafe,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Quick Actions Section
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Rounded.VerifiedUser,
                    title = "Trusted",
                    onClick = onNavigateToTrusted,
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    icon = Icons.Rounded.History,
                    title = "History",
                    onClick = onNavigateToHistory,
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    icon = Icons.Rounded.LocalShipping,
                    title = "Deliveries",
                    onClick = onNavigateToContext,
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    icon = Icons.Rounded.Analytics,
                    title = "Analytics",
                    onClick = onNavigateToAnalytics,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Today's Activity Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Activity",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextButton(onClick = onNavigateToHistory) {
                    Text("See All", color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Today's activity list items
        items(calls.take(5)) { call ->
            CallItemRow(
                call = call,
                onClick = { onNavigateToCallDetail(call.id) },
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun QuickActionButton(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

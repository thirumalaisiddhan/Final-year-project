package com.callguard.ai.ui.screens.settings

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callguard.ai.data.model.RiskThreshold
import com.callguard.ai.ui.theme.ContextBadge
import com.callguard.ai.ui.theme.RiskCritical
import com.callguard.ai.ui.theme.RiskSafe
import com.callguard.ai.ui.theme.RiskWarning

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateToAbout: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings & Protection Rules",
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

            // Main Security Controls
            Text(
                text = "Core Protection",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsCard {
                SettingsSwitchRow(
                    icon = Icons.Rounded.Shield,
                    iconColor = MaterialTheme.colorScheme.primary,
                    title = "Protection",
                    description = "Master switch for background call screening and risk analysis",
                    checked = settings.protectionEnabled,
                    onCheckedChange = { v -> viewModel.updateSettings { it.copy(protectionEnabled = v) } }
                )

                SettingsDivider()

                SettingsSwitchRow(
                    icon = Icons.Rounded.Block,
                    iconColor = RiskCritical,
                    title = "Automatic Blocking",
                    description = "Instantly reject calls with high spam score if no delivery context exists",
                    checked = settings.autoBlockingEnabled,
                    onCheckedChange = { v -> viewModel.updateSettings { it.copy(autoBlockingEnabled = v) } }
                )

                SettingsDivider()

                SettingsSwitchRow(
                    icon = Icons.Rounded.Warning,
                    iconColor = RiskWarning,
                    title = "Warn Before Blocking",
                    description = "Show interactive screening overlay before rejecting calls",
                    checked = settings.warnBeforeBlocking,
                    onCheckedChange = { v -> viewModel.updateSettings { it.copy(warnBeforeBlocking = v) } }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Context & Overrides
            Text(
                text = "Context & Safelists",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsCard {
                SettingsSwitchRow(
                    icon = Icons.Rounded.VerifiedUser,
                    iconColor = RiskSafe,
                    title = "Trusted Caller Override",
                    description = "Always allow calls from approved organizations and individuals",
                    checked = settings.trustedCallerOverride,
                    onCheckedChange = { v -> viewModel.updateSettings { it.copy(trustedCallerOverride = v) } }
                )

                SettingsDivider()

                SettingsSwitchRow(
                    icon = Icons.Rounded.LocalShipping,
                    iconColor = ContextBadge,
                    title = "Delivery Context Protection",
                    description = "Prevent false blocking when packages from Amazon/Flipkart are active",
                    checked = settings.deliveryContextEnabled,
                    onCheckedChange = { v -> viewModel.updateSettings { it.copy(deliveryContextEnabled = v) } }
                )

                SettingsDivider()

                SettingsSwitchRow(
                    icon = Icons.Rounded.Security,
                    iconColor = MaterialTheme.colorScheme.primary,
                    title = "Unknown Caller Warnings",
                    description = "Highlight unfamiliar phone numbers for behavioral analysis",
                    checked = settings.unknownCallerWarnings,
                    onCheckedChange = { v -> viewModel.updateSettings { it.copy(unknownCallerWarnings = v) } }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Risk Threshold Selector
            Text(
                text = "Risk Sensitivity Threshold",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Sensitivity sets the aggressiveness of the ML classification heuristic.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RiskThreshold.values().forEach { threshold ->
                            val isSelected = settings.riskThreshold == threshold
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.setRiskThreshold(threshold) },
                                label = {
                                    Text(
                                        text = when (threshold) {
                                            RiskThreshold.LOW -> "Low (>80)"
                                            RiskThreshold.MEDIUM -> "Medium (>60)"
                                            RiskThreshold.HIGH -> "High (>40)"
                                        },
                                        fontSize = 11.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // About Project Link
            Text(
                text = "Application & Project Info",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToAbout() }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "About CallGuard AI",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Final Year CSE Project • Architecture & ML Pipeline",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = "Open",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        content()
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.padding(end = 8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
    )
}

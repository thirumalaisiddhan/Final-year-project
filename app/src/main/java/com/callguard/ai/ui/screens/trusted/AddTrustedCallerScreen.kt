package com.callguard.ai.ui.screens.trusted

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.callguard.ai.data.model.TrustedCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrustedCallerScreen(
    viewModel: TrustedViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(TrustedCategory.PERSONAL) }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    var reasonForTrust by remember { mutableStateOf("") }
    var approvalDuration by remember { mutableStateOf("Permanent") }
    var durationDropdownExpanded by remember { mutableStateOf(false) }

    var notes by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    val saveSuccess by viewModel.saveSuccess.collectAsState()

    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            viewModel.resetSaveSuccess()
            onNavigateBack()
        }
    }

    val durations = listOf("Permanent", "30 Days", "7 Days", "Today Only")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Trusted Caller",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Cancel")
                    }
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Name
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = it.isBlank()
                },
                label = { Text("Caller / Organization Name *") },
                placeholder = { Text("e.g. Amazon Logistics, BlueDart, Dr. Sharma") },
                isError = nameError,
                supportingText = {
                    if (nameError) Text("Name is required", color = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Phone Number or Org Identifier
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    phoneError = it.isBlank()
                },
                label = { Text("Phone Number or Service Prefix *") },
                placeholder = { Text("e.g. +91 98765 43210 or AMZN-DELIVERY") },
                isError = phoneError,
                supportingText = {
                    if (phoneError) Text("Phone number or identifier is required", color = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Category Dropdown
            Text(
                text = "Category",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box {
                OutlinedTextField(
                    value = selectedCategory.displayName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { categoryDropdownExpanded = true }) {
                            Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Select")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { categoryDropdownExpanded = true },
                    shape = RoundedCornerShape(12.dp)
                )

                DropdownMenu(
                    expanded = categoryDropdownExpanded,
                    onDismissRequest = { categoryDropdownExpanded = false }
                ) {
                    TrustedCategory.values().forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.displayName) },
                            onClick = {
                                selectedCategory = category
                                categoryDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Reason for Trust
            OutlinedTextField(
                value = reasonForTrust,
                onValueChange = { reasonForTrust = it },
                label = { Text("Reason for Trust") },
                placeholder = { Text("e.g. Courier delivering package today") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Approval Duration Dropdown
            Text(
                text = "Approval Duration",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box {
                OutlinedTextField(
                    value = approvalDuration,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { durationDropdownExpanded = true }) {
                            Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Select")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { durationDropdownExpanded = true },
                    shape = RoundedCornerShape(12.dp)
                )

                DropdownMenu(
                    expanded = durationDropdownExpanded,
                    onDismissRequest = { durationDropdownExpanded = false }
                ) {
                    durations.forEach { d ->
                        DropdownMenuItem(
                            text = { Text(d) },
                            onClick = {
                                approvalDuration = d
                                durationDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Notes
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (Optional)") },
                placeholder = { Text("Additional contact info or verification notes") },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Buttons: Save Trusted Caller, Cancel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        val isNameValid = name.isNotBlank()
                        val isPhoneValid = phoneNumber.isNotBlank()
                        nameError = !isNameValid
                        phoneError = !isPhoneValid

                        if (isNameValid && isPhoneValid) {
                            viewModel.addTrustedCaller(
                                name = name,
                                numberOrId = phoneNumber,
                                category = selectedCategory,
                                reason = reasonForTrust,
                                duration = approvalDuration,
                                notes = notes
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(Icons.Rounded.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save Trusted Caller", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

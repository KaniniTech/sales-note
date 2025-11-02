package com.kaninitech.salesnote.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.kaninitech.salesnote.utils.DynamicStatusBar
import com.kaninitech.salesnote.viewmodel.BusinessDetPrefsViewModel
import kotlinx.coroutines.launch
import com.kaninitech.salesnote.R
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBusinessScreen(navController: NavController) {
    val backgroundColor = colorResource(id = R.color.jet)
    DynamicStatusBar(backgroundColor)
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    val businessViewModel: BusinessDetPrefsViewModel = koinViewModel()
    val bizData by businessViewModel.userBusinessData.collectAsState()

    var businessName by remember { mutableStateOf("") }
    var businessAddress by remember { mutableStateOf("") }
    var businessContact by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Add Business Details", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = backgroundColor
                    )
                )
            },
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Add Business Details",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
//                )
//            )

        containerColor = colorResource(id = R.color.white)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header section
            Text(
                text = "Enter your business information below.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Card for inputs
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // Business Name
                    OutlinedTextField(
                        value = businessName,
                        onValueChange = { businessName = it },
                        label = { Text("Business Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White,
                            focusedBorderColor = backgroundColor,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedLabelColor = backgroundColor,
                            cursorColor = backgroundColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    )

                    // Address
                    OutlinedTextField(
                        value = businessAddress,
                        onValueChange = { businessAddress = it },
                        label = { Text("Business Address") },
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White,
                            focusedBorderColor = backgroundColor,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedLabelColor = backgroundColor,
                            cursorColor = backgroundColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    )

                }
            }

            // Save button
            Button(
                onClick = {
                    if (businessName.isBlank() || businessAddress.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please fill all fields.")
                        }
                    } else {
                        // TODO: Handle save logic here (e.g., ViewModel call)
                        businessViewModel.saveBusinessData(businessName, businessAddress, generateSixDigitCode())
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Business details saved!")
                        }
                        focusManager.clearFocus()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id=R.color.jet)
                )
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }



        }
    }
}

fun generateSixDigitCode(): String {
    val timestamp = System.currentTimeMillis()
    val randomPart = (timestamp % 1_000_000).toInt() // take last 6 digits of timestamp
    return String.format("%06d", randomPart) // ensure it's always 6 digits
}

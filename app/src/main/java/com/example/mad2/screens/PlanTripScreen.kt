package com.example.travelapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanTripScreen(navController: NavHostController) {
    var placesID by remember { mutableStateOf("") } // State for user-entered ID
    var saveStatus by remember { mutableStateOf("") } // State to track save status

    Scaffold(
        topBar = { TopAppBar(title = { Text("Explore") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Welcome to the Plan Trip Screen",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Input Field for PlacesID
                TextField(
                    value = placesID,
                    onValueChange = { placesID = it },
                    label = { Text("Enter PlacesID") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = {
                        if (placesID.isNotBlank()) {
                            savePlacesIDToFirestore(placesID) { success ->
                                saveStatus = if (success) "PlacesID saved successfully!" else "Failed to save PlacesID."
                            }
                        } else {
                            saveStatus = "Please enter a valid PlacesID."
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(text = "Save PlacesID")
                }

                // Display Save Status
                if (saveStatus.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = saveStatus, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

// Function to save PlacesID to Firestore
fun savePlacesIDToFirestore(placesID: String, onComplete: (Boolean) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val placesRef = db.collection("places").document(placesID)

    // Data to save
    val data = mapOf("PlacesID" to placesID)

    placesRef.set(data)
        .addOnSuccessListener {
            onComplete(true) // Notify success
        }
        .addOnFailureListener {
            onComplete(false) // Notify failure
        }
}

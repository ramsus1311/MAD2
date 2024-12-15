package com.example.travelapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(navController: NavHostController) {
    // State to hold the list of places
    var places by remember { mutableStateOf<List<String>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    // Fetch places from Firestore
    LaunchedEffect(Unit) {
        fetchPlacesFromFirestore { fetchedPlaces ->
            places = fetchedPlaces
            loading = false
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Trip Details") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (loading) {
                // Show a loading indicator while fetching data
                CircularProgressIndicator()
            } else if (places.isEmpty()) {
                // Show a message if no places are found
                Text(text = "No places found.", style = MaterialTheme.typography.bodyLarge)
            } else {
                // Display the list of places
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(places) { place ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = place,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Function to fetch all places from Firestore
fun fetchPlacesFromFirestore(onComplete: (List<String>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val placesRef = db.collection("places")

    placesRef.get()
        .addOnSuccessListener { querySnapshot ->
            val placesList = querySnapshot.documents.mapNotNull { it.getString("PlacesID") }
            onComplete(placesList) // Pass the fetched places to the callback
        }
        .addOnFailureListener {
            onComplete(emptyList()) // Handle failure by returning an empty list
        }
}

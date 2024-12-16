package com.example.mad2.screens

import TripDetails
import TripListComponent
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mad2.model.Trip
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(navController: NavHostController) {
    var selectedTrip by remember { mutableStateOf<Trip?>(null) }
    var trips by remember { mutableStateOf<List<Trip>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        fetchTripsFromFirestore(onTripsFetched = {
            trips = it
            isLoading = false
        }, onError = {
            Log.e("Firestore", "Error fetching trips: $it")
            isLoading = false
        })
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Trip Details") }) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp))
                ) {
                    if (selectedTrip == null) {
                        TripListComponent(
                            trips = trips,
                            onTripSelect = { selectedTrip = it },
                            onDeleteTrip = { tripToDelete ->
                                deleteTripFromFirestore(tripToDelete) {
                                    trips = trips.filter { it.tripId != tripToDelete.tripId }
                                }
                            }
                        )
                    } else {
                        TripDetails(
                            trip = selectedTrip!!,
                            onBack = { selectedTrip = null },
                            onDeletePlace = { placeToDelete ->
                                val updatedPlaces = selectedTrip!!.placesOfInterest.filter { it != placeToDelete }
                                selectedTrip = selectedTrip!!.copy(placesOfInterest = updatedPlaces)
                                updateTripInFirestore(selectedTrip!!)
                            }
                        )
                    }
                }
            }
        }
    }
}

fun fetchTripsFromFirestore(
    onTripsFetched: (List<Trip>) -> Unit,
    onError: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    db.collection("trips").get()
        .addOnSuccessListener { result ->
            val trips = result.mapNotNull { it.toObject<Trip>() }
            onTripsFetched(trips)
        }
        .addOnFailureListener { exception ->
            onError(exception.localizedMessage ?: "Unknown error")
        }
}

fun deleteTripFromFirestore(trip: Trip, onComplete: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("trips").document(trip.tripId).delete()
        .addOnSuccessListener {
            Log.d("Firestore", "Trip deleted successfully")
            onComplete()
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error deleting trip: ${e.localizedMessage}")
        }
}

fun updateTripInFirestore(trip: Trip) {
    val db = FirebaseFirestore.getInstance()
    db.collection("trips").document(trip.tripId).set(trip)
        .addOnSuccessListener {
            Log.d("Firestore", "Trip updated successfully")
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error updating trip: ${e.localizedMessage}")
        }
}

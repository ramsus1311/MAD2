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
import com.example.mad2.model.PlaceOfInterest
import com.example.mad2.model.Trip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(navController: NavHostController) {
    var selectedTrip by remember { mutableStateOf<Trip?>(null) }
    val trips = remember { mutableStateOf<List<Trip?>>(emptyList()) }

    Scaffold(topBar = { TopAppBar(title = { Text("Trip Details") }) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp))
            ) {
                val currentTrips = trips.value.toMutableList()
                if (!currentTrips.contains(trip)) {
                    currentTrips.add(trip)
                    trips.value = currentTrips
                }
                if (selectedTrip == null) {
                    TripListComponent(
                        trips = trips,
                        onTripSelect = { selectedTrip = it },
                        onDeleteTrip = { Log.d("TRASHCAN CLICKED", "Trip deleted") }
                    )
                } else {
                    TripDetails(
                        trip = selectedTrip!!,
                        onBack = { selectedTrip = null },
                        onDeletePlace = { Log.d("TRASHCAN CLICKED", "Place deleted") }
                    )
                }
            }
        }
    }
}

val place1 = PlaceOfInterest(
    name = "Eiffel Tower",
    city = "Paris",
    country = "France",
    state = "Île-de-France",
    houseNumber = "5",
    street = "Champ de Mars",
    postcode = "75007",
    placeId = "poi1234"
)

val place2 = PlaceOfInterest(
    name = "Louvre Museum",
    city = "Paris",
    country = "France",
    state = "Île-de-France",
    houseNumber = "34",
    street = "Rue de Rivoli",
    postcode = "75001",
    placeId = "poi5678"
)

val place3 = PlaceOfInterest(
    name = "Colosseum",
    city = "Rome",
    country = "Italy",
    state = "Lazio",
    houseNumber = "1",
    street = "Piazza del Colosseo",
    postcode = "00184",
    placeId = "poi91011"
)

val trip = Trip(
    tripId = "1",
    name = "European Vacation",
    startDate = 1700000000L,
    endDate = 1702598400L,
    placesOfInterest = listOf(place1, place2, place3)
)

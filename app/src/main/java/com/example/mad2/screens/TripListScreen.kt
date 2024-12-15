package com.example.mad2.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mad2.model.PlaceOfInterest
import com.example.mad2.model.Trip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(navController: NavHostController) {
    // State to hold the list of places
    var places by remember { mutableStateOf<List<String>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    // Fetch places from Firestore
    /*
    LaunchedEffect(Unit) {
        fetchPlacesFromFirestore { fetchedPlaces ->
            places = fetchedPlaces
            loading = false
        }
    }
     */
    //composable("tripDetails/{tripId}")
    Scaffold(
        topBar = { TopAppBar(title = { Text("Trip List") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            var trips by remember { mutableStateOf<List<Trip>>(emptyList()) }
            trips += trip
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Trips",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
                HorizontalDivider(color = Color.Black, thickness = 2.dp)
                LazyColumn() {
                    items(trips) { trip ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column ( Modifier.clickable {
                                navController.navigate("")
                            }) {
                                Text(
                                    text = trip.name,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                val dateFormat =
                                    SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                                val formattedStartDate =
                                    trip.startDate.let { dateFormat.format(Date(it)) } ?: "N/A"
                                val formattedEndDate =
                                    trip.endDate.let { dateFormat.format(Date(it)) } ?: "N/A"
                                Text(
                                    text = "$formattedStartDate to $formattedEndDate",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Button( colors = ButtonDefaults.buttonColors( containerColor = Color.Red), onClick = {
                                Log.d("TRASHCAN CLICKED", "DELETED")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.White
                                )
                                Spacer(Modifier.size(8.dp))
                                Text("Delete")
                            }
                        }
                        HorizontalDivider(color = Color.Black, thickness = 1.dp)
                    }
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

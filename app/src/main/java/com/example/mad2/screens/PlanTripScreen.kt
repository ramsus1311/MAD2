package com.example.travelapp.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelapp.network.GeoapifyResponse
import com.example.travelapp.network.GeoapifyGeocodeResponse
import com.example.travelapp.network.Place
import com.example.travelapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.sp
import com.example.mad2.Model.CityDetails
import com.example.mad2.Model.PlaceOfInterest
import com.example.mad2.Network.GeoapifyApiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanTripScreen(navController: NavHostController, geoapifyApiHelper: GeoapifyApiHelper) {
    var query by remember { mutableStateOf("") }
    var cities by remember { mutableStateOf<List<CityDetails>>(emptyList()) }  // Search query
    var placesOfInterest by remember { mutableStateOf<List<PlaceOfInterest>>(emptyList()) }
    var selectedCity by remember { mutableStateOf<CityDetails?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedPlacesOfInterest by remember { mutableStateOf<List<PlaceOfInterest>>(emptyList()) }

    var showDialog by remember { mutableStateOf(false) }
    var tripName by remember { mutableStateOf("") }

    // Search bar composable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Plan Trip Screen", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        val scope = rememberCoroutineScope()

        // Search bar
        if (selectedCity == null) {
            // Show the TextField if no city is selected
            TextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    if (newQuery.isNotEmpty()) {
                        scope.launch {
                            isLoading = true
                            try {
                                val fetchedCities = geoapifyApiHelper.searchForCityByName(newQuery)
                                if (fetchedCities != null) {
                                    cities = fetchedCities
                                } else {
                                    // Handle empty result or error
                                    Log.e("Geoapify", "No cities found for the query: $newQuery")
                                }
                            } catch (e: Exception) {
                                Log.e("Geoapify", "Error: ${e.localizedMessage}")
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        cities = emptyList()
                    }
                },
                label = { Text("Search for a city") },
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn {
                items(cities) { city ->
                    Text(
                        text = "City: ${city.city}, ${city.state.takeIf { it.isNotEmpty() }?.let { "State: $it," } ?: ""} Country: ${city.country}",
                        modifier = Modifier.clickable {
                            selectedCity = city
                        }
                    )
                }
            }
        } else {
            Text(
                text = "${selectedCity!!.city}\n${selectedCity!!.state.takeIf { it.isNotEmpty() }?.let { "$it, " } ?: ""}${selectedCity!!.country}",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth().padding(16.dp).border(BorderStroke(2.dp, Color.Blue), shape = RoundedCornerShape(8.dp)).clickable {
                    selectedCity = null
                }
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the selected place's ID
        // Use LaunchedEffect to launch coroutines based on selectedCity
        LaunchedEffect(selectedCity) {
            if (selectedCity != null) {
                isLoading = true
                try {
                    // Fetch places of interest for the selected city
                    val fetchedPlacesOfInterest = geoapifyApiHelper.getPlacesOfInterestForCity(
                        selectedCity!!.placeId, "commercial"
                    )
                    // Update the state with the fetched places of interest
                    if (fetchedPlacesOfInterest != null) {
                        placesOfInterest = fetchedPlacesOfInterest
                    } else {
                        Log.e("Geoapify", "No places of interest found.")
                    }
                } catch (e: Exception) {
                    Log.e("Geoapify", "Error: ${e.localizedMessage}")
                } finally {
                    isLoading = false
                }
            }
        }

        if (placesOfInterest.isNotEmpty()) {
            Text("Places of Interest:",
                fontSize = 16.sp)
            LazyColumn(
                modifier = Modifier
                    .border(BorderStroke(2.dp, Color.Gray), shape = MaterialTheme.shapes.medium)
                    .fillMaxWidth()
            ) {
                items(placesOfInterest) { place ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        Text(text = place.name,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                        if (place !in selectedPlacesOfInterest) {
                            IconButton(onClick = {
                                selectedPlacesOfInterest = selectedPlacesOfInterest + place
                            },
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.Black)
                            }
                        } else {
                            IconButton(onClick = {
                                selectedPlacesOfInterest = selectedPlacesOfInterest.filter { it != place }

                            }, modifier = Modifier.padding(4.dp)
                                ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Remove", tint = Color.Red)
                            }
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth().background(if (selectedPlacesOfInterest.isNotEmpty()) Color.Blue else Color.Gray),
                enabled = selectedPlacesOfInterest.isNotEmpty()
            ) {
                Text(text = "Save Trip", color = Color.White)
            }
        }


        if (showDialog) {
            // Dialog for entering the trip name
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Enter Trip Name") },
                text = {
                    TextField(
                        value = tripName,
                        onValueChange = { tripName = it },
                        label = { Text("Trip Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Call your Firestore function here to save the trip


                            // Close the dialog
                            showDialog = false
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}


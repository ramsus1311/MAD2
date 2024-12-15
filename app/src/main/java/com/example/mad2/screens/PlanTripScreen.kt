package com.example.travelapp.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanTripScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }  // Search query
    var places by remember { mutableStateOf<List<Place>>(emptyList()) }
    var selectedPlaceId by remember { mutableStateOf<String?>(null) } // City ID after geocoding
    var placesOfInterest by remember { mutableStateOf<List<Place>>(emptyList()) }

    // Search bar composable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Plan Trip Screen", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar
        TextField(
            value = query,
            onValueChange = { newQuery ->
                query = newQuery
                if (newQuery.isNotEmpty()) {
                    getPlacesFromName(newQuery, "97c1c34c94dd4ee7968e556e2c36ed33", onSuccess = { fetchedPlaces ->
                        places = fetchedPlaces
                    }, onFailure = { error ->
                        Log.e("Geoapify", "Error: $error")
                    })
                }
            },
            label = { Text("Search for a city") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            items(places) { place ->
                ListItem(
                    headlineContent = {
                        Text(text = place.formatted)
                    },
                    modifier = Modifier.clickable {
                        selectedPlaceId = place.place_id
                    }
                )
            }
        }

        LaunchedEffect(selectedPlaceId) {
            selectedPlaceId?.let { id ->
                searchPlace( "commercial.food_and_drink.bakery", id, "97c1c34c94dd4ee7968e556e2c36ed33", onSuccess = { result ->
                    Log.d("Result", result.toString())
                    placesOfInterest = result
                }, onFailure = { error ->
                    Log.e("SearchResults", "Error: $error")
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the selected place's ID
        if (placesOfInterest.isNotEmpty()) {
            Text("Places of Interest:")
            LazyColumn(
                modifier = Modifier
                    .border(BorderStroke(2.dp, Color.Gray)) // Set border width and color
                    .padding(8.dp) // Optional padding inside the border
            ) {
                items(placesOfInterest) { place ->
                    Text(place.formatted)
                }
            }
        }
    }
}


// Function to get the cityId (place_id) from the city name using the Geoapify Geocoding API
fun getPlacesFromName(query: String, apiKey: String, onSuccess: (List<Place>) -> Unit, onFailure: (String) -> Unit) {
    RetrofitInstance.geoapifyService.getCitiesFromName(query, apiKey).enqueue(object : Callback<GeoapifyGeocodeResponse> {
        override fun onResponse(call: Call<GeoapifyGeocodeResponse>, response: Response<GeoapifyGeocodeResponse>) {

            if (response.isSuccessful) {
                val places = response.body()?.features?.map { feature ->
                    Place(
                        formatted = feature.properties.formatted,
                        place_id = feature.properties.place_id
                    )
                } ?: emptyList()

                onSuccess(places)
            } else {

                onFailure("Error: ${response.code()}")
            }
        }
        override fun onFailure(call: Call<GeoapifyGeocodeResponse>, t: Throwable) {
            onFailure("Failure: ${t.message}")
        }
    })
}

// Function to search for places using the cityId obtained from Geocoding
fun searchPlace(category: String, cityId: String, apiKey: String, onSuccess: (List<Place>) -> Unit, onFailure: (String) -> Unit) {
    RetrofitInstance.geoapifyService.searchPlace(category, "place:$cityId", 20,  apiKey, 0).enqueue(object : Callback<GeoapifyResponse> {
        override fun onResponse(call: Call<GeoapifyResponse>, response: Response<GeoapifyResponse>) {
            if (response.isSuccessful) {
                val placesOfInterest = response.body()?.features?.map { feature ->
                    Place(
                        formatted = feature.properties.formatted,
                        place_id = feature.properties.place_id
                    )
                } ?: emptyList()

                onSuccess(placesOfInterest)
            } else {

                onFailure("Error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<GeoapifyResponse>, t: Throwable) {
            onFailure("Failure: ${t.message}")
        }
    })
}

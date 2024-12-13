package com.example.travelapp.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelapp.network.GeoapifyResponse
import com.example.travelapp.network.RetrofitInstance
import com.example.travelapp.network.GeoapifyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanTripScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }  // Search query
    var results by remember { mutableStateOf<List<String>>(emptyList()) }  // Search results

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
                    // Call API whenever the query changes
                    searchPlace(newQuery, "commercial.books", "cityIdHere", "97c1c34c94dd4ee7968e556e2c36ed33", onSuccess = { places ->
                        results = places
                    }, onFailure = { error ->
                        Log.e("Geoapify", "Error: $error")
                    })
                }
            },
            label = { Text("Search for a city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display search results
        if (results.isNotEmpty()) {
            results.forEach { place ->
                Text(text = place)
            }
        }
    }
}

fun searchPlace(query: String, category: String, cityId: String, apiKey: String, onSuccess: (List<String>) -> Unit, onFailure: (String) -> Unit) {
    // Call the Geoapify API with the category, cityId, and apiKey
    RetrofitInstance.geoapifyService.searchPlace(category, "place:$cityId", 20, 0, apiKey).enqueue(object : Callback<GeoapifyResponse> {
        override fun onResponse(call: Call<GeoapifyResponse>, response: Response<GeoapifyResponse>) {
            if (response.isSuccessful) {
                // Extract place names from the response
                val places = response.body()?.results?.map { it.formatted } ?: emptyList()
                onSuccess(places)
            } else {
                onFailure("Error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<GeoapifyResponse>, t: Throwable) {
            onFailure("Failure: ${t.message}")
        }
    })
}

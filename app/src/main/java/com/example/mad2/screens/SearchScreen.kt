package com.example.mad2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mad2.viewmodel.CitysearchViewModel

@Composable
fun SearchScreen(viewModel: CitysearchViewModel, navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Search Bar
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Search for a city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Button
        Button(onClick = { viewModel.searchCities(searchQuery.value) }) {
            Text(text = "Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading Spinner or Error Message
        if (viewModel.isLoading.value) {
            Text(text = "Loading...")
        } else {
            // Show the places from the response
            val places = viewModel.places.value
            if (places != null && places.isNotEmpty()) {
                LazyColumn {
                    items(places) { place ->
                        Card(modifier = Modifier.padding(8.dp)) {
                            Text(text = place.properties?.name ?: "Unknown Place")
                        }
                    }
                }
            } else {
                Text(text = "No results found.")
            }

            // Error message
            if (viewModel.errorMessage.value != null) {
                Text(text = viewModel.errorMessage.value ?: "")
            }
        }
    }
}

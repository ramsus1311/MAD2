package com.example.mad2.screens // Ensure the package is correct

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding // If you need padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // Add Modifier import here
import androidx.compose.ui.unit.dp // Add dp import here
import androidx.navigation.NavController

@Composable
fun PlanTripScreen(navController: NavController) {
    // Add your PlanTripScreen UI components here
    Column(modifier = Modifier.padding(16.dp)) { // Example of using padding
        Text(text = "Plan Your Trip")
        Spacer(modifier = Modifier.height(16.dp)) // Use Modifier to define spacing
        // Add more UI elements as necessary

        // Example of a button to go back to the home screen
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Go Back to Home")
        }
    }
}

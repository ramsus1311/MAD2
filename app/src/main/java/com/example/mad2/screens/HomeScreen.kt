package com.example.travelapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Home") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Center content vertically
            ) {
                Text(text = "Welcome to the Home Screen", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate("tripDetails") }) {
                    Text("Go to Trip Details")
                }
                Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons
                Button(onClick = { navController.navigate("explore") }) {
                    Text("Explore")
                }
                Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons
                Button(onClick = { navController.navigate("profile") }) {
                    Text("Profile")
                }
            }
        }
    }
}

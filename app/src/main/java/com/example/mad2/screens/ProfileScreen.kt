package com.example.travelapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Profile") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Welcome to the Profile Screen",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Spacer to push the button to the bottom
                Spacer(modifier = Modifier.weight(1f))

                // Logout button with red background
                Button(
                    onClick = {
                        // Sign out the user from Firebase
                        FirebaseAuth.getInstance().signOut()

                        // Navigate to the login screen
                        navController.navigate("login") {
                            // Pop the Profile screen off the stack to prevent back navigation
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.5f) // Set button width to 50% of the screen
                        .align(Alignment.CenterHorizontally), // Center the button
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red // Set the button background color to red
                    )
                ) {
                    Text(text = "Logout", color = Color.White) // Set text color to white for contrast
                }
            }
        }
    }
}

package com.example.travelapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelapp.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


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
            // Insert the drawable image
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Home Background",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 250.dp) // Move the image upwards
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f)) // This pushes the text downward
                Text(
                    text = "Discover exciting locations, check the weather, and plan your next " +
                            "adventure with ease. Whether you're looking for scenic spots or " +
                            "hidden gems, our app helps you find the best places to visit and " +
                            "ensures you're prepared for the weather. Start exploring today " +
                            "and make your next trip unforgettable!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp, // Increase text size
                        fontWeight = FontWeight.SemiBold // Make text bold
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp) // Adjust spacing for the text
                        .fillMaxWidth() // Ensure it uses full width for proper centering
                        .wrapContentWidth(Alignment.CenterHorizontally) // Ensure centering
                )
            }
        }
    }
}


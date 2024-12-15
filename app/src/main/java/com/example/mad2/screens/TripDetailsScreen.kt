package com.example.mad2.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.mad2.model.Trip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Trip Details") }) }
    ) { innerPadding ->
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
                /*
                Text(
                    text = "Welcome to the Trip Details Screen",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
                HorizontalDivider(color = Color.Black, thickness = 2.dp)
                LazyColumn() {
                    items(placesOfInterest) place ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column ( Modifier.clickable {
                                Log.d("TRIP CLICKED", "CLACK")
                            }) {
                                Text(
                                    text = place.name,
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
                */
            }
        }
    }
}
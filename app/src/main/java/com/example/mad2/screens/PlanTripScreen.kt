package com.example.mad2.screens

import CitySearchResultsComponent
import FormattedDateTextComponent
import PlacesOfInterestListComponent
import SaveTripDialog
import SearchBarComponent
import SelectedCityComponent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mad2.components.DatePickerComponent
import com.example.mad2.model.CityDetails
import com.example.mad2.model.PlaceOfInterest
import com.example.mad2.model.Trip
import com.example.mad2.network.GeoapifyApiHelper
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

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
    var dateSelected by remember { mutableStateOf(false) }
    var tripName by remember { mutableStateOf("") }

    val dateRangePickerState = rememberDateRangePickerState()

    var selectedStartDate by remember { mutableStateOf<Long?>(null) }
    var selectedEndDate by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Plan your next trip!",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Log.d("DATE SELECTED", dateSelected.toString())

        if (!dateSelected) {
            DatePickerComponent(
                onDateSelected = {
                    selectedStartDate = dateRangePickerState.selectedStartDateMillis
                    selectedEndDate = dateRangePickerState.selectedEndDateMillis
                    dateSelected = true
                },
                dateRangePickerState = dateRangePickerState
            )
        } else {
            FormattedDateTextComponent(selectedStartDate, selectedEndDate, onResetDate = {
                dateSelected = false
            })
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (dateSelected) {
            if (selectedCity == null) {
                SearchBarComponent(
                    query = query,
                    onQueryChange = { query = it },
                    geoapifyApiHelper = geoapifyApiHelper,
                    onCitiesFetched = { cities = it },
                    onLoadingStateChange = { isLoading = it }
                )

                CitySearchResultsComponent(
                    cities = cities,
                    onCitySelected = { selectedCity = it }
                )
            } else {
                SelectedCityComponent(
                    selectedCity = selectedCity,
                    onResetSelection = { selectedCity = null }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LaunchedEffect(selectedCity) {
            if (selectedCity != null) {
                isLoading = true
                try {
                    val fetchedPlacesOfInterest = geoapifyApiHelper.getPlacesOfInterestForCity(
                        selectedCity!!.placeId, "commercial"
                    )
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

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        if (placesOfInterest.isNotEmpty()) {
            Column(modifier = Modifier.weight(1f)) {
                PlacesOfInterestListComponent(
                    placesOfInterest = placesOfInterest,
                    selectedPlacesOfInterest = selectedPlacesOfInterest,
                    onSelectPlace = { place ->
                        selectedPlacesOfInterest = selectedPlacesOfInterest + place
                    },
                    onRemovePlace = { place ->
                        selectedPlacesOfInterest = selectedPlacesOfInterest.filter { it != place }
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            var buttonColor = Color.Gray
            if (selectedPlacesOfInterest.isNotEmpty())
                buttonColor = Color.Blue

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(6.dp),
                enabled = selectedPlacesOfInterest.isNotEmpty()
            ) {
                Text(text = "Save Trip", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        if (showDialog) {
            SaveTripDialog(
                tripName = tripName,
                onTripNameChange = { tripName = it },
                onDismissDialog = { showDialog = false },
                onSaveTrip = {
                    saveTripToFirestore(
                        tripName = tripName,
                        startDate = selectedStartDate!!,
                        endDate = selectedEndDate!!,
                        placesOfInterest = selectedPlacesOfInterest,
                        onComplete = { success ->
                            if (success) {
                                Log.d("Firestore", "Trip saved successfully!")
                                showDialog = false
                                navController.popBackStack()
                            } else {
                                Log.e("Firestore", "Failed to save trip.")
                            }
                        }
                    )
                }
            )
        }
    }
}

fun saveTripToFirestore(
    tripName: String,
    startDate: Long,
    endDate: Long,
    placesOfInterest: List<PlaceOfInterest>,
    onComplete: (Boolean) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val tripId = UUID.randomUUID().toString()

    val trip = Trip(
        tripId = tripId,
        name = tripName,
        startDate = startDate,
        endDate = endDate,
        placesOfInterest = placesOfInterest
    )

    db.collection("trips").document(tripId)
        .set(trip)
        .addOnSuccessListener {
            onComplete(true) // Notify success
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error saving trip: ${e.localizedMessage}")
            onComplete(false) // Notify failure
        }
}

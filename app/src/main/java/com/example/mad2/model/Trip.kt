package com.example.mad2.model

data class Trip(
    val tripId: String = "",
    val name: String = "",
    val startDate: Long = 0L,
    val endDate: Long = 0L,
    val placesOfInterest: List<PlaceOfInterest> = emptyList()
)

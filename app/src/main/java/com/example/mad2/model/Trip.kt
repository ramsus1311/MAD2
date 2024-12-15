package com.example.mad2.model

data class Trip (
    val tripId: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val placesOfInterest: List<PlaceOfInterest>
)
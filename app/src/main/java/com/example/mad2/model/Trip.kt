package com.example.mad2.model

data class Trip (
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val placesOfInterest: List<PlaceOfInterest>
)
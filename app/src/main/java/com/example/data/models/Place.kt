package com.example.data.models

data class Place(
    val formatted: String,   // Full address (e.g., "New York, Krunska, 11000 Belgrade, Serbia")
    val place_id: String,    // Unique place identifier (ID)
)
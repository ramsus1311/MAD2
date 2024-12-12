package com.example.mad2.Dataclasses

data class Places(
    val features: List<Feature>
) {
    data class Feature(
        val properties: Properties?
    )

    data class Properties(
        val name: String?
    )
}

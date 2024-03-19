package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.Serializable


@Serializable
data class PropertyUploadResponse(
    val statusCode: Int,
    val message: String,
    val data: PropertyData
)

@Serializable
data class PropertyData(
    val property: Property
)

@Serializable
data class Property(
    val id: Int,
    val title: String,
    val description: String,
    val categoryId: Int,
    val rooms: Int,
    val price: Double,
    val availableDate: String,
    val features: List<String>,
    val location: Location,
    val images: List<String>
)

@Serializable
data class Location(
    val address: String,
    val county: String,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class Image(
    val id: Int,
    val url: String
)





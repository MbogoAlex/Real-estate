package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.Serializable
@Serializable
data class PropertyDetails (
    val title: String,
    val description: String,
    val categoryId: Int,
    val rooms: Int,
    val price: Double,
    val availableDate: String,
    val features: List<String>,
    val location: Location,
)

@Serializable
data class PropertyUploadResponse(
    val statusCode: Int,
    val message: String,
    val data: PropertyUploadResponseData
)

@Serializable
data class PropertyUploadResponseData(
    val property: PropertyUploadResponseDataBody
)

@Serializable
data class PropertyUploadResponseDataBody(
    val id: Int,
    val title: String,
    val description: String,
    val categoryId: Int,
    val rooms: Int,
    val price: Double,
    val availableDate: String,
    val features: List<String>,
    val location: Location,
    val images: List<Image>
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





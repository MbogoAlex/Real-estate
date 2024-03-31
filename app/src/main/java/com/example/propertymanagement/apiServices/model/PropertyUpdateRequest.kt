package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.Serializable
@Serializable
data class PropertyUpdateRequest (
    val title: String,
    val description: String,
    val categoryId: Int,
    val price: Double,
    val availableDate: String,
    val rooms: Int,
    val location: PropertyLocation,
    val features: List<String>,
)

@Serializable
data class PropertyLocation(
    val address: String,
    val county: String,
    val latitude: Double,
    val longitude: Double
)
@Serializable
data class PropertyUpdateResponse (
    val statusCode: Int,
    val message: String,
    val data: PropertyUpdateResponseData,
)
@Serializable
data class PropertyUpdateResponseData (
    val data: PropertyUpdateResponseDataProperty
)
@Serializable
data class PropertyUpdateResponseDataProperty (
    val user: User,
    val propertyId: Int,
    val title: String,
    val description: String,
    val categoryId: Int,
    val rooms: Int,
    val price: Double,
    val availableDate: String,
    val features: List<String>,
    val location: PropertyLocation,
    val images: List<PropertyUpdateImage>
)
@Serializable
data class User (
    val userId: Int,
    val email: String,
    val phoneNumber: String,
    val fname: String,
    val lname: String,
    val mname: String,
)
@Serializable
data class PropertyUpdateImage (
    val id: Int,
    val url: String
)
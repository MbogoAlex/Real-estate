package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.Serializable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody


data class PropertyDetails(
    val title: String,
    val description: String,
    val categoryId: Int,
    val price: Double,
    val availableDate: String,
    val location: PropertyLocation,
    val features: List<String>
)


data class PropertyLocation(
    val address: String,
    val county: String,
    val latitude: Double,
    val longitude: Double
)

val propertyMap: MutableMap<String, ResponseBody> = mutableMapOf()


@Serializable
data class PropertyUploadResponse(
    val statusCode: Int,
    val message: String
)

fun createPartFromString(stringData: String): RequestBody {
    return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
}


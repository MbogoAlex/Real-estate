package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProperty(
    val statusCode: Int,
    val message: String,
    val data: UserPropertyData
)
@Serializable
data class UserPropertyData (
    val properties: List<UserPropertyDataProperty>
)
@Serializable
data class UserPropertyDataProperty (
//    val user: Seller,
//    @SerialName(value = "propertyId")
    val propertyId: Int,
    val title: String,
    val description: String,
    val categoryId: Int,
    val price: Double,
    val availableDate: String,
    val features: List<String>,
    val location: Location,
    val images: List<Image>
)
//@Serializable
//data class Seller (
//    val userId: Int,
//    val email: String,
//    val phoneNumber: String,
//    val fname: String,
//    val lname: String,
//    val mname: String
//)
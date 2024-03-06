package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDetails(
    val fname: String,
    val mname: String,
    val lname: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
)

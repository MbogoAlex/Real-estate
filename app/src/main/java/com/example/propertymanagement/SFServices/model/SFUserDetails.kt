package com.example.propertymanagement.SFServices.model

data class SFUserDetails(
    val userId: Int,
    val userEmail: String,
    val userPhoneNumber: String,
    val userFirstName: String,
    val userLastName: String,
    val token: String
)

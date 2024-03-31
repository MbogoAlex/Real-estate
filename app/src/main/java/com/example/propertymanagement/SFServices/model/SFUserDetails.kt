package com.example.propertymanagement.SFServices.model

data class SFUserDetails(
    var userId: Int?,
    var userEmail: String,
    var userPhoneNumber: String,
    var userPassword: String,
    var userFirstName: String,
    var userLastName: String,
    var token: String
)

package com.example.propertymanagement.utils

data class LoggedInUserDetails (
    val userId: Int? = null,
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val token: String = "",
)
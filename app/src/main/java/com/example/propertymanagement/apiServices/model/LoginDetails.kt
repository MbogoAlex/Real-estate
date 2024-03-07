package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDetails(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponseStatus(
    val statusCode: Int,
    val message: String,
    val data: LoginData
)
@Serializable
data class LoginData(
    val user: LoginUser
)

@Serializable
data class LoginUser(
    val userInfo: LoginUserInfo,
    val token: String,
)
@Serializable
data class LoginUserInfo(
    val id: Int,
    val email : String,
    val phoneNumber: String,
    @SerialName(value = "mname")
    val middleName: String,
    @SerialName(value = "fname")
    val firstName: String,
    @SerialName(value = "lname")
    val lastName: String
)
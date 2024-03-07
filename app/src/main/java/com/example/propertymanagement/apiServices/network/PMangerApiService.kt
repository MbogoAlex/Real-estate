package com.example.propertymanagement.apiServices.network

import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.model.LoginResponseStatus
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.model.RegistrationResponseStatus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PMangerApiService {
    @POST("api/auth/register")
    public suspend fun registerUser(@Body user: RegistrationDetails): Response<RegistrationResponseStatus>

    @POST("api/auth/login")
    public suspend fun loginUser(@Body loginDetails: LoginDetails): Response<LoginResponseStatus>
}
package com.example.propertymanagement.apiServices.networkRepository

import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.model.LoginResponseStatus
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.network.PMangerApiService
import retrofit2.Response

interface PMangerApiRepository {
    suspend fun registerUser(user: RegistrationDetails)
    suspend fun loginUser(loginDetails: LoginDetails): Response<LoginResponseStatus>
}

class NetworkPManagerApiRepository(private val pManagerApiService: PMangerApiService): PMangerApiRepository {
    override suspend fun registerUser(user: RegistrationDetails) = pManagerApiService.registerUser(user)
    override suspend fun loginUser(loginDetails: LoginDetails): Response<LoginResponseStatus> = pManagerApiService.loginUser(loginDetails)
}
package com.example.propertymanagement.apiServices.networkRepository

import com.example.propertymanagement.apiServices.model.CategoriesResponse
import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.model.LoginResponseStatus
import com.example.propertymanagement.apiServices.model.PropertyDetails
import com.example.propertymanagement.apiServices.model.PropertyUploadResponse
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.model.RegistrationResponseStatus
import com.example.propertymanagement.apiServices.network.PMangerApiService
import okhttp3.MultipartBody
import retrofit2.Response

interface PMangerApiRepository {
    suspend fun registerUser(user: RegistrationDetails): Response<RegistrationResponseStatus>
    suspend fun loginUser(loginDetails: LoginDetails): Response<LoginResponseStatus>

    suspend fun createProperty(token: String, userId: String, property: PropertyDetails, images: List<MultipartBody.Part>): Response<PropertyUploadResponse>

    suspend fun getCategories(token: String): Response<CategoriesResponse>
}

class NetworkPManagerApiRepository(private val pManagerApiService: PMangerApiService): PMangerApiRepository {
    override suspend fun registerUser(user: RegistrationDetails): Response<RegistrationResponseStatus> = pManagerApiService.registerUser(user)
    override suspend fun loginUser(loginDetails: LoginDetails): Response<LoginResponseStatus> = pManagerApiService.loginUser(loginDetails)

    override suspend fun createProperty(
        token: String,
        userId: String,
        property: PropertyDetails,
        images: List<MultipartBody.Part>
    ): Response<PropertyUploadResponse> = pManagerApiService.uploadPropertyDetails(token, userId, property, images)

    override suspend fun getCategories(token: String): Response<CategoriesResponse> = pManagerApiService.getCategories(token)
}
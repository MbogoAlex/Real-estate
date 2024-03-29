package com.example.propertymanagement.apiServices.networkRepository

import com.example.propertymanagement.apiServices.model.CategoriesResponse
import com.example.propertymanagement.apiServices.model.Category
import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.model.LoginResponseStatus
import com.example.propertymanagement.apiServices.model.Property
import com.example.propertymanagement.apiServices.model.PropertyDetails
import com.example.propertymanagement.apiServices.model.PropertyUploadResponse
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.model.RegistrationResponseStatus
import com.example.propertymanagement.apiServices.model.SingleProperty
import com.example.propertymanagement.apiServices.network.PMangerApiService
import okhttp3.MultipartBody
import retrofit2.Response

interface PMangerApiRepository {
    suspend fun registerUser(user: RegistrationDetails): Response<RegistrationResponseStatus>
    suspend fun loginUser(loginDetails: LoginDetails): Response<LoginResponseStatus>

    suspend fun createProperty(token: String, userId: String, property: PropertyDetails, images: List<MultipartBody.Part>): Response<PropertyUploadResponse>

    suspend fun getCategories(token: String): Response<CategoriesResponse>

    suspend fun getAllListings(token: String): Response<Property>

    suspend fun getSpecificProperty(token: String, propertyId: String): Response<SingleProperty>

    suspend fun getPropertiesOfSpecificCategory(token: String, categoryId: String): Response<Property>
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

    override suspend fun getAllListings(token: String): Response<Property> = pManagerApiService.getAllListings(token)

    override suspend fun getSpecificProperty(token: String, propertyId: String): Response<SingleProperty> = pManagerApiService.getSpecificProperty(token, propertyId)

    override suspend fun getPropertiesOfSpecificCategory(token: String, categoryId: String): Response<Property> = pManagerApiService.getPropertiesOfSpecificCategory(
        token = token,
        categoryId = categoryId
    )
}
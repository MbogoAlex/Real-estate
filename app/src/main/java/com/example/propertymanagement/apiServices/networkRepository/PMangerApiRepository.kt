package com.example.propertymanagement.apiServices.networkRepository

import com.example.propertymanagement.apiServices.model.CategoriesResponse
import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.model.LoginResponseStatus
import com.example.propertymanagement.apiServices.model.PropertyUploadResponse
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.model.RegistrationResponseStatus
import com.example.propertymanagement.apiServices.network.PMangerApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart

interface PMangerApiRepository {
    suspend fun registerUser(user: RegistrationDetails): Response<RegistrationResponseStatus>
    suspend fun loginUser(loginDetails: LoginDetails): Response<LoginResponseStatus>

    suspend fun createProperty(partMap: MutableMap<String, RequestBody>, image: MultipartBody.Part): Response<PropertyUploadResponse>

    suspend fun getCategories(token: String): Response<CategoriesResponse>
}

class NetworkPManagerApiRepository(private val pManagerApiService: PMangerApiService): PMangerApiRepository {
    override suspend fun registerUser(user: RegistrationDetails): Response<RegistrationResponseStatus> = pManagerApiService.registerUser(user)
    override suspend fun loginUser(loginDetails: LoginDetails): Response<LoginResponseStatus> = pManagerApiService.loginUser(loginDetails)

    override suspend fun createProperty(
        partMap: MutableMap<String, RequestBody>,
        image: MultipartBody.Part
    ): Response<PropertyUploadResponse> = pManagerApiService.uploadPropertyDetails(partMap, image)

    override suspend fun getCategories(token: String): Response<CategoriesResponse> = pManagerApiService.getCategories(token)
}
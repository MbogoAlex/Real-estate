package com.example.propertymanagement.apiServices.network

import com.example.propertymanagement.apiServices.model.CategoriesResponse
import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.model.LoginResponseStatus
import com.example.propertymanagement.apiServices.model.PropertyUploadResponse
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.model.RegistrationResponseStatus
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface PMangerApiService {
    @POST("api/auth/register")
    public suspend fun registerUser(@Body user: RegistrationDetails): Response<RegistrationResponseStatus>

    @POST("api/auth/login")
    public suspend fun loginUser(@Body loginDetails: LoginDetails): Response<LoginResponseStatus>

    @Multipart
    @POST("api/property/create")
    suspend fun uploadPropertyDetails(
        @PartMap() partMap: MutableMap<String, RequestBody>,
        @Part file: MultipartBody.Part
    ): Response<PropertyUploadResponse>

    @GET("api/category")
    suspend fun getCategories(@Header("Authorization") token: String): Response<CategoriesResponse>
}
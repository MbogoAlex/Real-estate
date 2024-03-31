package com.example.propertymanagement.apiServices.network

import com.example.propertymanagement.apiServices.model.CategoriesResponse
import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.model.LoginResponseStatus
import com.example.propertymanagement.apiServices.model.Property
import com.example.propertymanagement.apiServices.model.PropertyDetails
import com.example.propertymanagement.apiServices.model.PropertyUpdateRequest
import com.example.propertymanagement.apiServices.model.PropertyUpdateResponse
import com.example.propertymanagement.apiServices.model.PropertyUploadResponse
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.model.RegistrationResponseStatus
import com.example.propertymanagement.apiServices.model.SingleProperty
import com.example.propertymanagement.apiServices.model.SpecificCategoryProperty
import com.example.propertymanagement.apiServices.model.UserProperty
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface PMangerApiService {
    @POST("api/auth/register")
    public suspend fun registerUser(@Body user: RegistrationDetails): Response<RegistrationResponseStatus>

    @POST("api/auth/login")
    public suspend fun loginUser(@Body loginDetails: LoginDetails): Response<LoginResponseStatus>

    @Multipart
    @POST("api/property/userId={id}/create")
    suspend fun uploadPropertyDetails(
        @Header("Authorization") token: String,
        @Path("id") userId: String,
        @Part("data") property: PropertyDetails,
        @Part imageFiles: List<MultipartBody.Part>
    ): Response<PropertyUploadResponse>

    @GET("api/category")
    suspend fun getCategories(@Header("Authorization") token: String): Response<CategoriesResponse>

    @Headers("Content-Type: application/json")
    @GET("api/property")
    suspend fun getAllListings(@Header("Authorization") token: String): Response<Property>
    @GET("api/property/propertyId={propertyId}")
    suspend fun getSpecificProperty(@Header("Authorization") token: String, @Path("propertyId") propertyId: String): Response<SingleProperty>
    @GET("api/property/categoryId={categoryId}")
    suspend fun getPropertiesOfSpecificCategory(@Header("Authorization") token: String, @Path("categoryId") categoryId: String): Response<SpecificCategoryProperty>

    @GET("api/property/userId={userId}")
    suspend fun getUserProperties(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Response<UserProperty>

    @PUT("api/property/propertyId={propertyId}/update")
    suspend fun updateProperty(
        @Body property: PropertyUpdateRequest,
        @Header("Authorization") token: String,
        @Path("propertyId") propertyId: Int,
    ): Response<PropertyUpdateResponse>
}
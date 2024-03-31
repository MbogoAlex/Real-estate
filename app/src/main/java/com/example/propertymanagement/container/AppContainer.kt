package com.example.propertymanagement.container

import android.content.Context
import com.example.propertymanagement.apiServices.network.PMangerApiService
import com.example.propertymanagement.apiServices.networkRepository.NetworkPManagerApiRepository
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppContainer {
    val pMangerApiRepository: PMangerApiRepository
}

class DefaultContainer(context: Context): AppContainer {
    private val json = Json { ignoreUnknownKeys = true }
//    private val baseUrl = "http://172.105.90.112:8080/pManager/"
    private val baseUrl = "http://192.168.147.6:8080/pManager/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: PMangerApiService by lazy {
        retrofit.create(PMangerApiService::class.java)
    }
    override val pMangerApiRepository: PMangerApiRepository by lazy {
        NetworkPManagerApiRepository(retrofitService)
    }

}

class FormDataContainer(context: Context): AppContainer {
//    private val baseUrl = "http://172.105.90.112:8080/pManager/"
    private val baseUrl = "http://192.168.147.6:8080/pManager/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: PMangerApiService by lazy {
        retrofit.create(PMangerApiService::class.java)
    }

    override val pMangerApiRepository: PMangerApiRepository by lazy {
        NetworkPManagerApiRepository(retrofitService)
    }
}
package com.example.propertymanagement.apiServices.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse (
    val statusCode: Int,
    val message: String,
    val data: Categories,
)
@Serializable
data class Categories (
    val categories: List<Category>
)
@Serializable
data class Category (
    @SerialName(value = "id")
    val categoryId: Int,
    val name: String
)
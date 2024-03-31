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
    val id: Int,
    val name: String
)
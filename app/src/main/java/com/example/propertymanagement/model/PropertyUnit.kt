package com.example.propertymanagement.model

import androidx.annotation.StringRes
import com.example.propertymanagement.ui.views.ListingsType

data class PropertyUnit(
    val id: Int,
    @StringRes val name : Int,
    val listingsType: ListingsType,
    @StringRes val description: Int,
    @StringRes val location: Int,
    val images: List<UnitImage>,
    val seller: Seller
)

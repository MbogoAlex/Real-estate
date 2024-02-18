package com.example.propertymanagement.model

import androidx.annotation.StringRes

data class Unit(
    val id: Int,
    @StringRes val name : Int,
    val unitType: UnitType,
    @StringRes val description: Int,
    @StringRes val location: Int,
    val images: List<UnitImage>,
    val seller: Seller
)

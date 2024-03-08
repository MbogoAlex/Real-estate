package com.example.propertymanagement.ui.state

import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.BottomTab
import com.example.propertymanagement.model.PropertyUnit
import com.example.propertymanagement.ui.views.ListingsType

data class AppUiState(
    val units: Map<ListingsType, List<PropertyUnit>> = emptyMap(),
    val unfilteredUnits: List<PropertyUnit> = emptyList(),
    val unitType: ListingsType = ListingsType.RENTALS,
    val selectedUnit: PropertyUnit = Datasource.units.first { it.id == 1 },
    val currentTab: BottomTab = BottomTab.UNITS,
    val showUnitDetails: Boolean = false,
    val showFilteredUnits: Boolean = false,
    val isRegistered: Boolean = false

) {
    val unitsToDisplay: List<PropertyUnit> by lazy {
        if(showFilteredUnits) {
            units[unitType]!!
        } else {
            unfilteredUnits
        }
    }
}

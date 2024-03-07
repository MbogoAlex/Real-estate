package com.example.propertymanagement.ui.state

import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.Tab
import com.example.propertymanagement.model.Unit
import com.example.propertymanagement.model.UnitType

data class AppUiState(
    val units: Map<UnitType, List<Unit>> = emptyMap(),
    val unfilteredUnits: List<Unit> = emptyList(),
    val unitType: UnitType = UnitType.BED_SITTER,
    val selectedUnit: Unit = Datasource.units.first { it.id == 1 },
    val currentTab: Tab = Tab.UNITS,
    val showUnitDetails: Boolean = false,
    val showFilteredUnits: Boolean = false,
    val isRegistered: Boolean = false

) {
    val unitsToDisplay: List<Unit> by lazy {
        if(showFilteredUnits) {
            units[unitType]!!
        } else {
            unfilteredUnits
        }
    }
}

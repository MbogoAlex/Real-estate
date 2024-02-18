package com.example.propertymanagement.ui.appViewModel

import androidx.lifecycle.ViewModel
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.UnitType
import com.example.propertymanagement.ui.state.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(value = AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun intializeApp() {
        val allUnits = Datasource.units
        _uiState.value = AppUiState(
            units = allUnits.groupBy { it.unitType },
            unfilteredUnits = allUnits,
            unitType = UnitType.BED_SITTER,
            selectedUnit = allUnits.first { it.id == 1 },
            showUnitDetails = false,
            showFilteredUnits = false,
        )
    }

    init {
        intializeApp()
    }

    fun filterUnits(unitType: UnitType) {
        _uiState.update {
            it.copy(
                unitType = unitType,
                showFilteredUnits = true,
            )
        }
    }

    fun showUnitDetails(selectedUnitId: Int) {
        _uiState.update {
            it.copy(
                selectedUnit = Datasource.units.first { unitId -> unitId.id == selectedUnitId },
                showUnitDetails = true,
            )
        }
    }

    fun onBackButtonClicked() {
        _uiState.update {
            it.copy(
                showUnitDetails = false
            )
        }
    }
}
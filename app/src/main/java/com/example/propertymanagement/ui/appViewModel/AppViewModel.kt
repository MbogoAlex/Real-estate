package com.example.propertymanagement.ui.appViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.BottomTab
import com.example.propertymanagement.ui.state.AppUiState
import com.example.propertymanagement.ui.views.ListingsType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(value = AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    fun intializeApp() {
        val allUnits = Datasource.units
        _uiState.value = AppUiState(
            units = allUnits.groupBy { it.listingsType },
            unfilteredUnits = allUnits,
            unitType = ListingsType.RENTALS,
            selectedUnit = allUnits.first { it.id == 1 },
            currentTab = BottomTab.UNITS,
            showUnitDetails = false,
            showFilteredUnits = false,
        )
    }

    init {
        intializeApp()
        checkIfRegistered()
    }

    fun filterUnits(unitType: ListingsType) {

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



    fun checkIfRegistered() {
        viewModelScope.launch {
            pManagerSFRepository.sfUserId.collect {userId ->
                Log.i("CUR_USER", userId.toString())
                if(userId != null) {
                    _uiState.update {
                        it.copy(
                            isRegistered = true
                        )
                    }
                }
            }
        }

    }

    fun switchTab(tab: BottomTab) {
        _uiState.update {
            it.copy(
                currentTab = tab
            )
        }
    }
}
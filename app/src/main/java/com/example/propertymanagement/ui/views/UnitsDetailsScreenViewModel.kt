package com.example.propertymanagement.ui.views

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.PropertyUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UnitDetailsUiState(
    val propertyUnit: PropertyUnit = Datasource.units[0],
    val userRegistered: Boolean = false
)
class UnitsDetailsScreenViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val unitId: String? = savedStateHandle[UnitDetailsScreenDestination.unitId]
    var userRegistered: Boolean = false
    private val _uiState = MutableStateFlow(value = UnitDetailsUiState())
    val uiState: StateFlow<UnitDetailsUiState> = _uiState.asStateFlow()

    fun checkIfUserIsRegistered() {
        viewModelScope.launch {
            val userId = pManagerSFRepository.sfUserId.collect() { userId ->
                userRegistered = userId != null
            }
        }
    }

    fun loadProperty() {
        val property = Datasource.units.first {
            it.id == unitId!!.toInt()
        }
        _uiState.update {
            it.copy(
                propertyUnit = property
            )
        }
    }

    init {
        loadProperty()
        checkIfUserIsRegistered()
    }

}
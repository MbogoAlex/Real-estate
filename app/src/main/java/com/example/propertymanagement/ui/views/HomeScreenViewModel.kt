package com.example.propertymanagement.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.BottomTab
import com.example.propertymanagement.model.PropertyUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ListingsType {
    RENTALS,
    AIRBNB
}

enum class PropertyRooms {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN
}

data class BottomBarUiState(
    val bottomTab: BottomTab = BottomTab.UNITS
)
data class ListingsUiState(
    val units: Map<ListingsType, List<PropertyUnit>> = emptyMap(),
    val listingsType: ListingsType = ListingsType.RENTALS,
    val propertyRooms: PropertyRooms = PropertyRooms.ONE,
) {
    val unitsToDisplay: List<PropertyUnit> by lazy {
        units[listingsType]!!
    }
}
class HomeScreenViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository
): ViewModel() {

    var userRegistered: Boolean = false
    //bottom bar viewModel

    private val _bottomBarUiState = MutableStateFlow(value = BottomBarUiState())
    val bottomBarUiState: StateFlow<BottomBarUiState> = _bottomBarUiState.asStateFlow()

    //listings viewModel
    private val _listingsUiState = MutableStateFlow(value = ListingsUiState())
    val listingsUiState: StateFlow<ListingsUiState> = _listingsUiState.asStateFlow()




    fun initialUnits() {
        val units = Datasource.units
        _listingsUiState.value = ListingsUiState(
            units = units.groupBy {
                it.listingsType
            },
            listingsType = ListingsType.RENTALS,
        )
    }

    init {
        initialUnits()
        checkIfUserRegistered()
    }

    fun checkIfUserRegistered() {
        viewModelScope.launch {
            pManagerSFRepository.sfUserId.collect() { userId ->
                userRegistered = userId != null
            }
        }
    }

    fun filterUnits(listingsType: ListingsType, propertyRooms: PropertyRooms) {
        _listingsUiState.update {
            it.copy(
                listingsType = listingsType,
                propertyRooms = propertyRooms
            )
        }
    }

    fun switchTab(bottomTab: BottomTab) {
        _bottomBarUiState.update {
            it.copy(
                bottomTab = bottomTab
            )
        }
    }
}
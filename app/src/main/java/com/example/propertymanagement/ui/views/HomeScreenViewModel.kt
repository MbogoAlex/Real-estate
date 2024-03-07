package com.example.propertymanagement.ui.views

import androidx.lifecycle.ViewModel
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository

enum class ListingsType {
    RENTALS,
    AIRBNB
}

enum class RentalRooms {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN
}
data class ListingUiState(
    private val units: List<Unit>,
    private val listingsType: ListingsType,
    private val showContact: Boolean,
)
class HomeScreenViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository
): ViewModel() {

}
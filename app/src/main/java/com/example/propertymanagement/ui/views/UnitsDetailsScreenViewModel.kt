package com.example.propertymanagement.ui.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.model.Image
import com.example.propertymanagement.apiServices.model.Location
import com.example.propertymanagement.apiServices.model.SingleProperty
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.PropertyUnit
import com.example.propertymanagement.utils.LoggedInUserDetails
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PropertyDetails (
    val title: String = "",
    val description: String = "",
    val images: List<PropertyImage>,
    val location: PropertyLocation
)

data class PropertyImage(
    val id: Int = 0,
    val url: String = ""
)

fun Image.toPropertyImage(): PropertyImage = PropertyImage(
    id = id,
    url = url
)

data class PropertyLocation (
    val address: String = "",
    val county: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

fun Location.toPropertyLocation(): PropertyLocation = PropertyLocation(
    address = address,
    county = county,
    latitude = latitude,
    longitude = longitude
)
data class UnitDetailsUiState(
    val propertyUnit: PropertyDetails,
    val userRegistered: Boolean = false
)
class UnitsDetailsScreenViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val unitId: String? = savedStateHandle[UnitDetailsScreenDestination.unitId]
    var userRegistered: Boolean = false
    private val _uiState = MutableStateFlow(value = UnitDetailsUiState(
        propertyUnit = PropertyDetails(
            title = "",
            description = "",
            images = mutableListOf(),
            location = PropertyLocation()
        )
    ))
    val uiState: StateFlow<UnitDetailsUiState> = _uiState.asStateFlow()

    var userDetails = LoggedInUserDetails()

    fun checkIfUserIsRegistered() {
        viewModelScope.launch {
            val userId = pManagerSFRepository.sfUserId.collect() { userId ->
                userRegistered = userId != null
                _uiState.update {
                    it.copy(
                        userRegistered = userRegistered
                    )
                }
            }
        }
    }

    fun loadProperty() {
        val property = Datasource.units.first {
            it.id == unitId!!.toInt()
        }
//        _uiState.update {
//            it.copy(
//                propertyUnit = property
//            )
//        }
    }

    fun lodUserDetails() {
        viewModelScope.launch {
            pManagerSFRepository.userDetails.collect() {
                userDetails = it.toLoggedInUserDetails()
                getSpecificProperty(
                    token = userDetails.token,
                    propertyId = unitId.toString()
                )
            }
        }
    }

    fun getSpecificProperty(token: String, propertyId: String) {
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.getSpecificProperty(token = "Bearer $token", propertyId = propertyId)
                if(response.isSuccessful) {
                    var images = response.body()?.data?.property?.images?.forEach { image ->
                        image.copy(
                            url = image.url.replace("172.105.90.112", "172.105.90.112:8080")
                        )
                    }
                    val property = response.body()?.data?.property?.images?.map {
                        it.copy(url = it.url.replace("", "")).toPropertyImage()
                    }?.let {
                        response.body()?.data?.property?.location?.toPropertyLocation()?.let { it1 ->
                            PropertyDetails(
                                title = response.body()?.data?.property?.title!!,
                                description = response.body()?.data?.property?.description!!,
                                images = it,
                                location = it1
                            )
                        }
                    }
                    _uiState.update {
                        it.copy(
                            propertyUnit = property!!
                        )
                    }

                    } else {
                        Log.i("SPECIFIC_PROPERTY_LOADING_ERROR", response.toString())
                }
                } catch (e: Exception) {
                    Log.e("FAILED_TO_FETCH_SPECIFIC_PROPERTY", e.toString())
            }
        }
    }

    init {
//        loadProperty()
        checkIfUserIsRegistered()
        lodUserDetails()
    }

}
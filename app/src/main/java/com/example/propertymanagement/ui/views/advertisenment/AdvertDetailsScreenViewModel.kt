package com.example.propertymanagement.ui.views.advertisenment

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.model.Image
import com.example.propertymanagement.apiServices.model.Location
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.ui.views.PropertyDetails
import com.example.propertymanagement.ui.views.PropertyImage
import com.example.propertymanagement.ui.views.PropertyLocation
import com.example.propertymanagement.ui.views.UnitDetailsScreenDestination
import com.example.propertymanagement.ui.views.toPropertyImage
import com.example.propertymanagement.ui.views.toPropertyLocation
import com.example.propertymanagement.utils.LoggedInUserDetails
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdvertDetailsScreenUiState (
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val images: List<Image> = emptyList(),
    val location: Location = Location("", "", 0.0, 0.0),
    val price: Double = 0.0,
    val userDetails: LoggedInUserDetails = LoggedInUserDetails(),
    val rooms: Int = 0,
    val availableDate: String = "",
    val features: List<String> = emptyList(),

    )
class AdvertDetailsScreenViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _uiState = MutableStateFlow(value = AdvertDetailsScreenUiState())
    val uiState: StateFlow<AdvertDetailsScreenUiState> = _uiState.asStateFlow()
    val propertyId: String? = savedStateHandle[UserAdvertDetailsScreenDestination.unitId]

    var userDetails: LoggedInUserDetails = LoggedInUserDetails()


    fun getSpecificProperty(token: String, propertyId: String) {
        Log.i("LOADING_SPECIFIC", "LOADING_SPECIFIC")
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.getSpecificProperty(token = "Bearer $token", propertyId = propertyId)

                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            id = response.body()?.data?.property?.propertyId!!.toString(),
                            images = response.body()?.data?.property?.images!!,
                            title = response.body()?.data?.property?.title!!,
                            description = response.body()?.data?.property?.title!!,
                            location = response.body()?.data?.property?.location!!,
                            price = response.body()?.data?.property?.price!!,
                            rooms = response.body()?.data?.property?.rooms!!,
                            availableDate = response.body()?.data?.property?.availableDate!!,
                            features = response.body()?.data?.property?.features!!,
                        )
                    }


                } else {
                    Log.i("SPECIFIC_PROPERTY_LOADING_ERRORZ", "token: ${userDetails.token}, unitId: $propertyId")
                }
            } catch (e: Exception) {
                Log.e("FAILED_TO_FETCH_SPECIFIC_PROPERTY", e.toString())
            }
        }
    }

    fun loadStartUpDetails() {
        var token = ""
        viewModelScope.launch {
            pManagerSFRepository.userDetails.collect() {
                userDetails = it.toLoggedInUserDetails()
                _uiState.update {uiState ->
                    uiState.copy(
                        userDetails = userDetails
                    )
                }
                Log.i("USER_DATA_DS", userDetails.toString())
                token = userDetails.token
                getSpecificProperty(token, propertyId!!)
            }
        }

    }

    init {
        loadStartUpDetails()
    }
}
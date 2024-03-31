package com.example.propertymanagement.ui.views.propertyUpdate

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.model.Category
import com.example.propertymanagement.apiServices.model.Image
import com.example.propertymanagement.apiServices.model.PropertyLocation
import com.example.propertymanagement.apiServices.model.PropertyUpdateRequest
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.ui.views.advertisenment.UserAdvertDetailsScreenDestination
import com.example.propertymanagement.utils.LoggedInUserDetails
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class UpdateStatus {
    INITIAL,
    START,
    DONE,
    FAIL,
}
data class GeneralPropertyDetails(
    val propertyId: String = "",
    val categoryId: Int = 0,
    val type: String = "Type",
    val rooms: String = "Rooms",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val county: String = "",
    val address: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val price: String = "",
    val features: List<String> = mutableListOf(),
    val images: List<Image> = mutableListOf(),

)

data class PropertyUpdateUiState (
    val generalPropertyDetails: GeneralPropertyDetails = GeneralPropertyDetails(),
    val categories: List<Category> = mutableListOf(),
    val userDetails: LoggedInUserDetails = LoggedInUserDetails(),
    val updateStatus: UpdateStatus = UpdateStatus.INITIAL
)
class PropertyUpdateScreenViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _uiState = MutableStateFlow(value = PropertyUpdateUiState())
    val uiState: StateFlow<PropertyUpdateUiState> = _uiState.asStateFlow()
    val propertyId: String? = savedStateHandle[PropertyUpdateScreenDestination.propertyId]
    var generalPropertyDetails by mutableStateOf(GeneralPropertyDetails())
    var features by mutableStateOf(mutableStateListOf(""))
    var userDetails by mutableStateOf(LoggedInUserDetails())


    fun updatePropertyTitle(title: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            title = title
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updatePropertyDescription(description: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            description = description
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updatePropertyAddress(address: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            address = address
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updatePropertyCounty(county: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            county = county
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updatePropertyPrice(price: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            price = price
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updatePropertyCategory(categoryId: Int, type: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            categoryId = categoryId,
            type = type
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updatePropertyRooms(rooms: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            rooms = rooms
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updateAvailabilityDate(date: String) {
        generalPropertyDetails = generalPropertyDetails.copy(
            date = date
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun updateFeatureField(index: Int, value: String) {
        features[index] = value
        generalPropertyDetails = generalPropertyDetails.copy(
            features = features
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun removeFeatureField(index: Int) {
        features.removeAt(index)
        generalPropertyDetails = generalPropertyDetails.copy(
            features = features
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun addNewField() {
        features.add("")
        generalPropertyDetails = generalPropertyDetails.copy(
            features = features
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }

    fun getSpecificProperty(token: String, propertyId: String) {
        Log.i("LOADING_SPECIFIC", "LOADING_SPECIFIC, token: $token, id: $propertyId")
        viewModelScope.launch {
            try {

                val response = pManagerApiRepository.getSpecificProperty(token = "Bearer $token", propertyId = propertyId)
                features.addAll(response.body()?.data?.property?.features!!)
                generalPropertyDetails = generalPropertyDetails.copy(

                    propertyId = response.body()?.data?.property?.propertyId!!.toString(),
                    images = response.body()?.data?.property?.images!!,
                    title = response.body()?.data?.property?.title!!,
                    description = response.body()?.data?.property?.title!!,
                    address = response.body()?.data?.property?.location?.address!!,
                    county = response.body()?.data?.property?.location?.county!!,
                    price = response.body()?.data?.property?.price.toString(),
//                            rooms = response.body()?.data?.property?.rooms!!,
                    date = response.body()?.data?.property?.availableDate!!,
                    features = features,
                )

                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            generalPropertyDetails = generalPropertyDetails
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

    fun getCategories() {
        Log.i("USER_TOKEN", "Bearer ${userDetails.token}")
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.getCategories("Bearer ${userDetails.token}")
                if(response.isSuccessful) {
                    Log.i("FETCHING_CATEGORIES_STATUS", response.isSuccessful.toString())
                    _uiState.update {
                        it.copy(
                            categories = response.body()!!.data.categories
                        )
                    }
                } else {
                    Log.i("FETCHING_CATEGORIES_STATUS", response.isSuccessful.toString())
                }
            } catch (e: Exception) {
                Log.e("FETCHING_CATEGORIES_STATUS", "Error fetching categories: ${e.message}")
            }


        }
    }



    fun getCategoriesAndFetchProperty(token: String) {
        getSpecificProperty(
            token = token,
            propertyId = propertyId!!
        )
        getCategories()
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
                getCategoriesAndFetchProperty(token)
            }
        }

    }

    fun updateProperty() {
        _uiState.update {
            it.copy(
                updateStatus = UpdateStatus.START
            )
        }
        val token = userDetails.token
        val propertyId = propertyId!!
        val location = PropertyLocation(
            address = _uiState.value.generalPropertyDetails.address,
            county = _uiState.value.generalPropertyDetails.county,
            latitude = 0.0,
            longitude = 0.0
        )

        val property = PropertyUpdateRequest(
            title = _uiState.value.generalPropertyDetails.title,
            description = _uiState.value.generalPropertyDetails.description,
            categoryId = _uiState.value.generalPropertyDetails.categoryId,
            price = _uiState.value.generalPropertyDetails.price.toDouble(),
            availableDate = _uiState.value.generalPropertyDetails.date,
            rooms = _uiState.value.generalPropertyDetails.rooms.toInt(),
            location = location,
            features = _uiState.value.generalPropertyDetails.features,

        )
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.updateProperty(
                    token = token,
                    propertyId = propertyId.toInt(),
                    property = property
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            updateStatus = UpdateStatus.DONE
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            updateStatus = UpdateStatus.FAIL
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        updateStatus = UpdateStatus.FAIL
                    )
                }
            }
        }
    }

    fun stopLoadingToPreviousPage() {
        _uiState.update {
            it.copy(
                updateStatus = UpdateStatus.INITIAL
            )
        }
    }

    fun initializeStatus() {
        _uiState.update {
            it.copy(
                updateStatus = UpdateStatus.INITIAL
            )
        }
    }

    init {
        loadStartUpDetails()
        generalPropertyDetails = generalPropertyDetails.copy(
            features = features
        )
        _uiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }
}
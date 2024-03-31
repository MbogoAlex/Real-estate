package com.example.propertymanagement.ui.views.advertisenment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.model.Category
import com.example.propertymanagement.apiServices.model.PropertyDataProperty
import com.example.propertymanagement.apiServices.model.UserPropertyDataProperty
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.utils.LoggedInUserDetails
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserAdvertisementsUiState(
    val userDetails: LoggedInUserDetails = LoggedInUserDetails(),
    val listingsData: ListingsData = ListingsData()
)
data class ListingsData(
    val listings: List<UserPropertyDataProperty> = emptyList(),
    val categories: List<Category> = mutableListOf(),

)
class UserAdvertsViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = UserAdvertisementsUiState())
    val uiState: StateFlow<UserAdvertisementsUiState> = _uiState.asStateFlow()

    var userDetails: LoggedInUserDetails = LoggedInUserDetails()
    var listingsData: ListingsData = ListingsData()

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
                fetchListings(
                    token = userDetails.token,
                    userId = userDetails.userId.toString()
                )
            }
        }

    }

    init {
        loadStartUpDetails()
    }

    fun fetchListings(userId: String, token: String) {
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.getUserProperties(
                    token = "Bearer $token",
                    userId = userId
                )
                if(response.isSuccessful) {
                    listingsData = listingsData.copy(
                        listings = response.body()?.data?.properties!!
                    )
                    Log.i("USER_LISTINGS_FETCHED", "${response.body()?.data?.properties!!}")
                    _uiState.update {
                        it.copy(
                            listingsData = listingsData
                        )
                    }
                } else {
                    Log.i("FETCHING_USER_LISTINGS_UNSUCCESSFUL", "${response.body()?.data?.properties!!}")
                }
            } catch (e: Exception) {
                Log.e("FAILED_TO_FETCH_LISTINGS_OF_USER: $userId: ", e.toString())
            }
        }

    }

}
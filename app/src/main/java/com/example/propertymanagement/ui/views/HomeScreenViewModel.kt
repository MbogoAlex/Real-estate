package com.example.propertymanagement.ui.views

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.model.Category
import com.example.propertymanagement.apiServices.model.PropertyDataProperty
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.model.BottomTab
import com.example.propertymanagement.utils.LoggedInUserDetails
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ListingsType {
    RENTALS,
    AIRBNB
}

enum class ListingsLoadingState{
    INITIAL,
    LOADING,
    SUCCESS,
    FAILURE
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

data class HomeScreenUiState(
    val bottomTab: BottomTab = BottomTab.UNITS,
    val listingsData: ListingsData = ListingsData(),
    val isLoggedIn: Boolean = false,
    val forceRegister: Boolean = false,
    val forceLogin: Boolean = false,
    val userDetails: LoggedInUserDetails = LoggedInUserDetails(),
    var listingsLoadingState: ListingsLoadingState = ListingsLoadingState.INITIAL
)


data class ListingsData(
    val listings: List<PropertyDataProperty> = emptyList(),
    val categories: List<Category> = mutableListOf()
)

class HomeScreenViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(value = HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()



    var userDetails: LoggedInUserDetails = LoggedInUserDetails()
    var listingsData: ListingsData = ListingsData()


    fun initialUnits() {
//        val units = Datasource.units
//        _listingsUiState.value = ListingsUiState(
//            units = units.groupBy {
//                it.listingsType
//            },
//            listingsType = ListingsType.RENTALS,
//        )
    }

    fun loadStartUpDetails() {
        _uiState.update {
            it.copy(
                listingsLoadingState = ListingsLoadingState.LOADING
            )
        }
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
                getCategoriesAndFetchListings(token)
            }
        }

    }

    init {
        initialUnits()
        checkIfUserRegistered()
        loadStartUpDetails()
//        fetchCategories()

    }

    fun checkIfUserRegistered() {
        viewModelScope.launch {
            pManagerSFRepository.sfUserId.collect() { userId ->
                _uiState.update {
                    it.copy(
                        isLoggedIn = userId != null
                    )
                }
            }
        }
    }

    fun fetchListings(categoryId: String, token: String) {
        Log.i("CATEGORIZING_UNITS", "CATEGORIZING_UNITS: CATEGORY $categoryId, TOKEN: $token")
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.getPropertiesOfSpecificCategory(
                    token = "Bearer $token",
                    categoryId = categoryId
                )
                if(response.isSuccessful) {
                    listingsData = listingsData.copy(
                        listings = response.body()?.data?.categories!!
                    )
                    _uiState.update {
                        it.copy(
                            listingsData = listingsData
                        )
                    }
                } else {
                    Log.i("FETCHING_UNSUCCESSFUL", "${response.body()?.data?.categories!!}")
                }
            } catch (e: Exception) {
                Log.e("FAILED_TO_FETCH_LISTINGS_OF_ID $categoryId: ", e.toString())
            }
        }

    }

    fun switchTab(bottomTab: BottomTab) {
        _uiState.update {
            it.copy(
                bottomTab = bottomTab
            )
        }
    }




    fun getCategoriesAndFetchListings(token: String) {
        Log.i("USER_TOKEN", "Bearer $token")
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.getCategories("Bearer ${userDetails.token}")
                if(response.isSuccessful) {

                    Log.i("FETCHING_CATEGORIES_STATUS", response.isSuccessful.toString())
                    listingsData = listingsData.copy(
                        categories = response.body()!!.data.categories
                    )
                    val firstCategoryId = listingsData.categories[0].id
                    fetchListings(firstCategoryId.toString(), token)
                    _uiState.update {
                        it.copy(
                            listingsData = listingsData,
                            listingsLoadingState = ListingsLoadingState.SUCCESS
                        )
                    }

                    Log.i("CATEGORIES_FETCHED_ARE", "${_uiState.value.listingsData.categories}")
                } else {
                    _uiState.update {
                        it.copy(
                            listingsLoadingState = ListingsLoadingState.FAILURE
                        )
                    }
                    Log.i("FETCHING_CATEGORIES_STATUS", response.isSuccessful.toString())
                    handleLoginLogic()
                }
            } catch (e: Exception) {
                Log.e("FETCHING_CATEGORIES_STATUS", "Error fetching categories: ${e.message}")
                handleLoginLogic()
            }


        }
    }


    fun handleLoginLogic() {
        Log.i("LOGIN_LOGIC", "HANDLING LOGIN LOGIC")
        if(userDetails.userId != null) {
            Log.i("USER_DETAILS_NOT_NULL", "${userDetails.userId != null}")
            _uiState.update {
                it.copy(
//                    forceLogin = true,
                    forceRegister = true,
                    userDetails = userDetails
                )
            }
        } else if (userDetails.userId == null) {
            Log.i("USER_DETAILS_NULL", "${userDetails.userId == null}")
            _uiState.update {
                it.copy(
                    forceRegister = true
                )
            }
        }
        Log.i("PROCEED_TO_REGISTRATION", "force login? ${_uiState.value.forceLogin}, force register? ${_uiState.value.forceRegister}")
    }

    fun undoForcedLogin() {
        _uiState.update {
            it.copy(
                forceLogin = false
            )
        }
    }

    fun undoForcedRegistration() {
        _uiState.update {
            it.copy(
                forceRegister = false
            )
        }
    }
}
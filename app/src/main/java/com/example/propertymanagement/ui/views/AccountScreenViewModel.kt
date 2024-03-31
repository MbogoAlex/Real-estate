package com.example.propertymanagement.ui.views

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.utils.LoggedInUserDetails
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AccountUiState(
    val userDetails: LoggedInUserDetails = LoggedInUserDetails()
)


class AccountScreenViewModel(
    val pManagerSFRepository: PManagerSFRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()
    var userDetails: LoggedInUserDetails = LoggedInUserDetails()
    fun loadUserDetails() {
        viewModelScope.launch {
            pManagerSFRepository.userDetails.collect() { sfUserDetails ->
                userDetails = sfUserDetails.toLoggedInUserDetails()
                _uiState.update {
                    it.copy(
                        userDetails = userDetails
                    )
                }
                Log.i("ACCOUNT_USER_DETAILS", "$userDetails")
            }
        }
    }

    fun userLogout() {
        viewModelScope.launch {
            pManagerSFRepository.deleteAllPreferences()
        }
    }

    fun changeProfilePicture() {

    }


    init {
        loadUserDetails()
    }


}
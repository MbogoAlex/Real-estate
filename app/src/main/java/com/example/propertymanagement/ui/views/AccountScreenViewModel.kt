package com.example.propertymanagement.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoggedInUserDetails (
    val userId: Int? = null,
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val token: String = "",
)



class AccountScreenViewModel(
    val pManagerSFRepository: PManagerSFRepository
): ViewModel() {
    private val _accountScreenUiState = MutableStateFlow(value = LoggedInUserDetails())
    val accountScreenUiState: StateFlow<LoggedInUserDetails> = _accountScreenUiState.asStateFlow()

    fun loadUserDetails() {
        viewModelScope.launch {
            pManagerSFRepository.userDetails.collect() { sfUserDetails ->
                _accountScreenUiState.value = sfUserDetails.toLoggedInUserDetails()
            }
        }
    }

    init {
        loadUserDetails()
    }

    fun userLogout() {
        viewModelScope.launch {
            pManagerSFRepository.deleteAllPreferences()
        }
    }
}
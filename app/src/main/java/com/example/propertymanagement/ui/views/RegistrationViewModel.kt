package com.example.propertymanagement.ui.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.apiServices.model.RegistrationDetails
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserDetails(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)

data class RegistrationScreenUiState(
    val userDetails: UserDetails = UserDetails("", "", "", "", "", ""),
    val buttonEnabled: Boolean
)
class RegistrationViewModel(private val pMangerApiRepository: PMangerApiRepository): ViewModel() {
    var userDetails by mutableStateOf(UserDetails("", "", "", "", "", ""))
    private val _uiState = MutableStateFlow(value = RegistrationScreenUiState(
        buttonEnabled = false
    ))
    val uiState: StateFlow<RegistrationScreenUiState> = _uiState.asStateFlow()

    fun checkIfFieldsAreFilled(): Boolean {
        return !(userDetails.email.isEmpty() || userDetails.firstName.isEmpty() || userDetails.lastName.isEmpty() || userDetails.phoneNumber.isEmpty() || userDetails.password.isEmpty())
    }

    fun updateRegistrationScreenUi() {
        _uiState.update {
            it.copy(
                userDetails = userDetails,
                buttonEnabled = checkIfFieldsAreFilled()
            )
        }
    }

    fun registerUser() {
        viewModelScope.launch {
            pMangerApiRepository.registerUser(userDetails.toUser())
        }
    }
}

fun UserDetails.toUser(): RegistrationDetails = RegistrationDetails(
    fname = firstName,
    mname = middleName,
    lname = lastName,
    email = email,
    phoneNumber = phoneNumber,
    password = password
)
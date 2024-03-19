package com.example.propertymanagement.ui.views

import android.util.Log
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

enum class RegistrationStatus {
    START,
    LOADING,
    SUCCESS,
    FAIL,
}
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
    val registrationSuccess: Boolean,
    val buttonEnabled: Boolean,
    val registrationStatus: RegistrationStatus
)
class RegistrationViewModel(private val pMangerApiRepository: PMangerApiRepository): ViewModel() {
    var userDetails by mutableStateOf(UserDetails("", "", "", "", "", ""))
    private val _uiState = MutableStateFlow(value = RegistrationScreenUiState(
        buttonEnabled = checkIfFieldsAreFilled(),
        registrationSuccess = false,
        registrationStatus = RegistrationStatus.START
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
        _uiState.update {
            it.copy(
                registrationStatus = RegistrationStatus.LOADING
            )
        }
        viewModelScope.launch {
            val response = pMangerApiRepository.registerUser(userDetails.toUser())
            Log.i("LOGIN_TAG", response.toString())
            Log.i("LOGIN_SUCCESS", response.isSuccessful.toString())
            if(response.isSuccessful) {
                _uiState.update {
                    it.copy(
                        registrationSuccess = true,
                        registrationStatus = RegistrationStatus.SUCCESS
                    )
                }
                Log.i("UI_UPDATE", _uiState.value.registrationSuccess.toString())
            } else {
                _uiState.update {
                    it.copy(
                        registrationStatus = RegistrationStatus.FAIL
                    )
                }
            }
        }
    }

    fun updateRegistrationStatusToDone() {
        _uiState.update {
            it.copy(
                registrationStatus = RegistrationStatus.START
            )
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
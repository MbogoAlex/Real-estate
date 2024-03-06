package com.example.propertymanagement.ui.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.apiServices.model.LoginDetails
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUserDetails(
    val phoneNumber: String,
    val password: String
)

data class LoginUiState(
    val loginUserDetails: LoginUserDetails = LoginUserDetails("", ""),
    val buttonEnabled: Boolean
)
class LoginViewModel(private val pMangerApiRepository: PMangerApiRepository): ViewModel() {
    var loginUserDetails by mutableStateOf(LoginUserDetails("", ""))
    private val _uiState = MutableStateFlow(LoginUiState(buttonEnabled = false))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUiState() {
        if(loginUserDetails.phoneNumber.isNotEmpty() && loginUserDetails.password.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    loginUserDetails = loginUserDetails,
                    buttonEnabled = true
                )
            }
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            pMangerApiRepository.loginUser(loginUserDetails.toLoginDetails())
        }
    }
}

fun LoginUserDetails.toLoginDetails(): LoginDetails = LoginDetails(
    username = phoneNumber,
    password = password
)
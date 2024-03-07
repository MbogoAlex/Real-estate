package com.example.propertymanagement.ui.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.SFServices.model.SFUserDetails
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
    val buttonEnabled: Boolean,
    val loginSuccessful: Boolean,
)
class LoginViewModel(
    private val pMangerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val phoneNumber: String? = savedStateHandle[LoginScreenDestination.phoneNumber]
    private val password: String? = savedStateHandle[LoginScreenDestination.password]
    var loginUserDetails by mutableStateOf(LoginUserDetails(phoneNumber ?: "", password ?: ""))
    private val _uiState = MutableStateFlow(LoginUiState(
        buttonEnabled = checkIfFieldsAreFilled(),
        loginSuccessful = false
    ))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    lateinit var sfUserDetails: SFUserDetails

    fun updateUiState() {
        _uiState.update {
            it.copy(
                loginUserDetails = loginUserDetails,
                buttonEnabled = checkIfFieldsAreFilled()
            )
        }
    }

    fun checkIfFieldsAreFilled(): Boolean {
        return !(loginUserDetails.phoneNumber.isEmpty() || loginUserDetails.password.isEmpty())
    }

    fun loginUser() {
        viewModelScope.launch {
            val response = pMangerApiRepository.loginUser(loginUserDetails.toLoginDetails())
            if(response.isSuccessful) {
                sfUserDetails = response.body()?.data?.user?.token?.let {
                    SFUserDetails(
                        token = it,
                        userId = response.body()?.data!!.user.userInfo.id,
                        userFirstName = response.body()?.data!!.user.userInfo.firstName,
                        userLastName = response.body()?.data!!.user.userInfo.lastName,
                        userEmail = response.body()?.data!!.user.userInfo.email,
                        userPhoneNumber = response.body()?.data!!.user.userInfo.phoneNumber
                    )
                }!!
                Log.i("USER_DETAILS", sfUserDetails.toString())
                pManagerSFRepository.saveUserDetails(sfUserDetails)
                _uiState.update {
                    it.copy(
                        loginSuccessful = true
                    )
                }
            }

        }
    }
}

fun LoginUserDetails.toLoginDetails(): LoginDetails = LoginDetails(
    username = phoneNumber,
    password = password
)
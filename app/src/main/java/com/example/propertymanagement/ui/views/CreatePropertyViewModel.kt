package com.example.propertymanagement.ui.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FeaturesInputFieldsUiState (
    var features: List<String> = mutableListOf()
)

data class GeneralPropertyDataUiState (
    val generalPropertyDetails: GeneralPropertyDetails = GeneralPropertyDetails(
        title = "",
        description = "",
        date = "",
        county = "",
        address  = "",
        latitude = "",
        longitude = "",
        price = ""
    ),
    val showCreateButton: Boolean = false
)

data class GeneralPropertyDetails(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val county: String = "",
    val address: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val price: String = ""
)
class CreateNewPropertyViewModel: ViewModel() {
    private val _featuresInputFieldsUiState = MutableStateFlow(value = FeaturesInputFieldsUiState())
    val featuresInputFieldsUiState: StateFlow<FeaturesInputFieldsUiState> = _featuresInputFieldsUiState.asStateFlow()

    private val _generalPropertyDataUiState = MutableStateFlow(value = GeneralPropertyDataUiState())
    val generalPropertyDataUiState: StateFlow<GeneralPropertyDataUiState> = _generalPropertyDataUiState.asStateFlow()

    var generalPropertyDetails by mutableStateOf(GeneralPropertyDetails())


    var features by mutableStateOf(mutableStateListOf(""))


    fun updateGeneralUiState() {
        _generalPropertyDataUiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails,
                showCreateButton = showCreatePropertyButton()
            )
        }
    }

    init {
        _featuresInputFieldsUiState.value.features = features
    }



    fun showCreatePropertyButton(): Boolean {
        return !(
                generalPropertyDetails.address.isEmpty() || generalPropertyDetails.title.isEmpty() ||
                        generalPropertyDetails.description.isEmpty() || generalPropertyDetails.county.isEmpty() ||
                        generalPropertyDetails.price.isEmpty()
                )
    }
    fun addNewField() {

        features.add("")
        _featuresInputFieldsUiState.update {
            it.copy(
                features = features
            )
        }

    }

    fun updateFeaturesFieldUiState() {
        _featuresInputFieldsUiState.update {
            it.copy(
                features = features
            )
        }
    }

    fun removeFeatureField(index: Int) {
        features.removeAt(index)
        _featuresInputFieldsUiState.update {
            it.copy(
                features = features
            )
        }
    }
}
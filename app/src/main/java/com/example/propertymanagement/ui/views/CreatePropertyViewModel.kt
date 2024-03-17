package com.example.propertymanagement.ui.views

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.networkRepository.NetworkPManagerApiRepository
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FeaturesInputFieldsUiState (
    var features: List<String> = mutableListOf()
)

data class ImagesUiState (
    var images: List<Uri> = mutableListOf()
)

data class GeneralPropertyDataUiState (
    val generalPropertyDetails: GeneralPropertyDetails = GeneralPropertyDetails(
        type = "",
        rooms = "",
        title = "",
        description = "",
        date = "",
        county = "",
        address  = "",
        latitude = "",
        longitude = "",
        price = "",
        features = mutableListOf(),
        images = mutableListOf(),
    ),
    val showCreateButton: Boolean = false,
    val showPreview:  Boolean = false
)

data class GeneralPropertyDetails(
    val type: String = "",
    val rooms: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val county: String = "",
    val address: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val price: String = "",
    val features: List<String> = mutableListOf(),
    val images: List<Uri> = mutableListOf()
)
class CreateNewPropertyViewModel(
    private val pManagerApiRepository: PMangerApiRepository,
    private val pManagerSFRepository: PManagerSFRepository,
): ViewModel() {
    private val _featuresInputFieldsUiState = MutableStateFlow(value = FeaturesInputFieldsUiState())
    val featuresInputFieldsUiState: StateFlow<FeaturesInputFieldsUiState> = _featuresInputFieldsUiState.asStateFlow()

    private val _generalPropertyDataUiState = MutableStateFlow(value = GeneralPropertyDataUiState())
    val generalPropertyDataUiState: StateFlow<GeneralPropertyDataUiState> = _generalPropertyDataUiState.asStateFlow()

    private val _imagesUiState = MutableStateFlow(value = ImagesUiState())
    val imagesUiState: StateFlow<ImagesUiState> = _imagesUiState.asStateFlow()

    var generalPropertyDetails by mutableStateOf(GeneralPropertyDetails())


    var features by mutableStateOf(mutableStateListOf(""))

    var images by mutableStateOf(mutableStateListOf<Uri>())


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
        _generalPropertyDataUiState.update {
            it.copy(
                generalPropertyDetails = generalPropertyDetails.copy(
                    features = features
                )
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

    fun updateImagesUiState() {
//        images.add(image)
        _imagesUiState.update {
            it.copy(
                images = images
            )
        }
    }

}
package com.example.propertymanagement.ui.views

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertymanagement.SFServices.PManagerSFRepository
import com.example.propertymanagement.apiServices.model.Category
import com.example.propertymanagement.apiServices.model.Location
import com.example.propertymanagement.apiServices.model.PropertyDetails
import com.example.propertymanagement.apiServices.networkRepository.PMangerApiRepository
import com.example.propertymanagement.utils.toLoggedInUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

data class FeaturesInputFieldsUiState (
    var features: List<String> = mutableListOf()
)

data class ImagesUiState (
    var images: List<File> = mutableListOf()
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
    val categories: List<Category> = mutableListOf(),
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

    var images by mutableStateOf(mutableStateListOf<File>())

    var userDetails: LoggedInUserDetails = LoggedInUserDetails()



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
        loadUserDetails()
        getCategories()
    }

    fun loadUserDetails() {
        viewModelScope.launch {
            pManagerSFRepository.userDetails.collect() {
                userDetails = it.toLoggedInUserDetails()
            }
        }
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

    fun getCategories() {
        Log.i("USER_TOKEN", "Bearer ${userDetails.token}")
        viewModelScope.launch {
            try {
                val response = pManagerApiRepository.getCategories("Bearer ${userDetails.token}")
                if(response.isSuccessful) {
                    Log.i("FETCHING_CATEGORIES_STATUS", response.isSuccessful.toString())
                    _generalPropertyDataUiState.update {
                        it.copy(
                            categories = response.body()!!.data.categories
                        )
                    }
                } else {
                    Log.i("FETCHING_CATEGORIES_STATUS", response.isSuccessful.toString())
                }
            } catch (e: Exception) {
                Log.e("FETCHING_CATEGORIES_STATUS", "Error fetching categories: ${e.message}")
            }


        }
    }

    fun uploadProperty(categoryId: Int, context: Context) {
        Log.i("UPLOAD_PROPERTY", "uploadProperty function called")
        var imageParts = ArrayList<MultipartBody.Part>()
        _imagesUiState.value.images.forEach {file ->
            Log.i("IMAGE_PATH", file.path)
            val imageFile = File(file.path)
            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("imageFiles", imageFile.name, requestFile)
            imageParts.add(imagePart)
        }

        for(part in imageParts) {
            Log.i("IMAGE_PARTS", part.toString())
        }
        val location = Location(
            address = _generalPropertyDataUiState.value.generalPropertyDetails.address,
            county = _generalPropertyDataUiState.value.generalPropertyDetails.county,
            latitude = 0.0,
            longitude = 0.0
        )
        val images = _imagesUiState.value.images.map {
            it.toString()
        }


        val property = PropertyDetails(
            title = _generalPropertyDataUiState.value.generalPropertyDetails.title,
            description = _generalPropertyDataUiState.value.generalPropertyDetails.description,
            categoryId = categoryId,
            rooms = _generalPropertyDataUiState.value.generalPropertyDetails.rooms.toInt(),
            price = _generalPropertyDataUiState.value.generalPropertyDetails.price.toDouble(),
            availableDate = _generalPropertyDataUiState.value.generalPropertyDetails.date,
            features = _featuresInputFieldsUiState.value.features,
            location = location,
        )


        viewModelScope.launch {
            try {
                Log.i("UPLOAD_PROPERTY", "Before making network request")

                val response = pManagerApiRepository.createProperty(
                    token = "Bearer ${userDetails.token}",
                    userId = userDetails.userId.toString(),
                    property = property,
                    images = imageParts
                )

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.i("CREATE_PROPERTY_RESPONSE", "Status Code: ${responseBody.statusCode}, Message: ${responseBody.message}")
                    } else {
                        Log.i("CREATE_PROPERTY_RESPONSE", "Response body is null")
                    }
                } else {
                    Log.e("CREATE_PROPERTY_RESPONSE", "Unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CREATE_PROPERTY_EXCEPTION", "Exception: ${e.message}")
            }
        }
    }



    fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }



}
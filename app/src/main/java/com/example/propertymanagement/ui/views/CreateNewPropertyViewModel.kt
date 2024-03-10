package com.example.propertymanagement.ui.views

import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FeaturesInputFieldsUiState (
    val inputFields: List<FeaturesInputField> = mutableListOf()
)
class CreateNewPropertyViewModel: ViewModel() {
    private val _featuresInputFieldsUiState = MutableStateFlow(value = FeaturesInputFieldsUiState())
    val featuresInputFieldsUiState: StateFlow<FeaturesInputFieldsUiState> = _featuresInputFieldsUiState.asStateFlow()

    fun initialField() {
        var inputFields = mutableListOf<FeaturesInputField>()
        var initialField = FeaturesInputField(
            label = "",
            value = "",
            keyboardType = KeyboardType.Text,
            onValueChanged = {}
        )
        inputFields.add(initialField)
        _featuresInputFieldsUiState.update {
            it.copy(
                inputFields = inputFields
            )
        }
    }

    init {
        initialField()
    }
    fun addNewField() {
        var currentInputFields = _featuresInputFieldsUiState.value.inputFields
        var inputFields = mutableListOf<FeaturesInputField>()
        val newInputField = FeaturesInputField(
            label = "",
            value = "",
            keyboardType = KeyboardType.Text,
            onValueChanged = {}
        )



        inputFields.addAll(currentInputFields)
        inputFields.add(newInputField)

        _featuresInputFieldsUiState.update {
            it.copy(
                inputFields = inputFields
            )
        }
    }

    fun removeFeatureField(index: Int) {
        var inputFields = _featuresInputFieldsUiState.value.inputFields
        var fields = mutableListOf<FeaturesInputField>()
        fields.addAll(inputFields)
        fields.removeAt(index)
        _featuresInputFieldsUiState.update {
            it.copy(
                inputFields = fields
            )
        }
    }
}
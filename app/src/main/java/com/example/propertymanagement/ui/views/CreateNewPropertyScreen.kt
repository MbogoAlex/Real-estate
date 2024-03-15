package com.example.propertymanagement.ui.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertymanagement.R
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.example.propertymanagement.utils.convertMillisToDate

@Composable
fun CreateNewPropertyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(ScrollState(0))
    ) {
        Card() {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row {
                    PropertyTypeSelection(
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    NumberOfRoomsSelection(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                //title field
                InputField(
                    label = "Title",
                    value = "",
                    onValueChanged = { /*TODO*/ },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                //description field
                InputField(
                    label = "Description",
                    value = "",
                    onValueChanged = { /*TODO*/ },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                AvailabilitySelection()
                LocationSelection(
                    addressValue = "",
                    countyValue = "",
                    latitudeValue = "",
                    longitudeValue = "",
                    onAddressValueChanged = {},
                    onLatitudeValueChanged = {},
                    onLongitudeValueChanged = {},
                    onCountyValueChanged = {}
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Price",
                    fontWeight = FontWeight.Bold
                )
                InputField(
                    label = "Price",
                    value = "",
                    onValueChanged = {},
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Add features",
                    fontWeight = FontWeight.Bold
                )
                PropertyFeatures()

            }
        }
    }
}

@Composable
fun PropertyTypeSelection(
    modifier: Modifier = Modifier
) {
    val propertyTypes = listOf<String>("Rental", "Airbnb")
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedType by remember {
        mutableStateOf("")
    }


    Box(
        modifier = modifier
//            .widthIn(200.dp)
            .clickable {
                expanded = !expanded
            }
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = Modifier


        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                if(selectedType.isEmpty()) {
                    Text("Type")
                } else {
                    Text(text = selectedType)
                }
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select property type"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
//                    .padding(20.dp)
                    .background(color = Color.Transparent)
                    .width(160.dp)
            ) {
                for(type in propertyTypes) {
                    DropdownMenuItem(
                        text = { Text(text = type) },
                        onClick = {
                            selectedType = type
                            expanded = false
                        }
                    )
                    Divider()
                }
            }

        }
    }


}

@Composable
fun NumberOfRoomsSelection(
    modifier: Modifier = Modifier
) {
    val numOfRooms = listOf<String>("Bedsitter", "1", "2", "3", "4", "5")
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedType by remember {
        mutableStateOf("")
    }


    Box(
        modifier = modifier
//            .widthIn(200.dp)
            .clickable {
                expanded = !expanded
            }
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                if(selectedType.isEmpty()) {
                    Text(text = "Rooms")
                } else {
                    if(selectedType != "Bedsitter") {
                        Text(text = "$selectedType rooms")
                    } else {
                        Text(text = "$selectedType")
                    }

                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select number of rooms"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
//                    .padding(20.dp)
                    .background(color = Color.Transparent)
                    .width(160.dp)
            ) {
                for(num in numOfRooms) {
                    DropdownMenuItem(
                        text = { Text(text = num) },
                        onClick = {
                            selectedType = num
                            expanded = false
                        }
                    )
                    Divider()
                }
            }

        }
    }
}

@Composable
fun InputField(
    keyboardType: KeyboardType,
    label: String,
    value: String,
    onValueChanged: (newValue: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        label = {
                Text(text = label)
        },
        onValueChange = {
            onValueChanged(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailabilitySelection(
    modifier: Modifier = Modifier
) {
    val options = listOf<String>("Now", "Pick date")
    var selected by remember {
        mutableStateOf(options[0])
    }
    var availableDate by remember {
        mutableStateOf("")
    }
    var openDialog by remember {
        mutableStateOf(true)
    }
    val state = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis > System.currentTimeMillis()
            }
        }
    )
    val selectedDate = state.selectedDateMillis?.let { 
        convertMillisToDate(it)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Availability",
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(
                        selected = selected == option,
                        onClick = {
                            selected = option
                            if(option == "Pick date") {
                                openDialog = true
                            }
                        }
                    )
                    Text(text = option)
                }
            }

        }

        if(selected == "Now") {

            Text(text = "Available from: NOW")
        } else {
            if(openDialog) {
                DatePickerDialog(
                    onDismissRequest = { openDialog = false },
                    confirmButton = {
                                    TextButton(onClick = {
                                        openDialog = false
                                        availableDate = selectedDate!!
                                    }) {
                                        Text(text = "OK")
                                    }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDialog = false }) {
                            Text(text = "CANCEL")
                        }
                    }
                ) {
                    DatePicker(state = state)

                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Available from: $availableDate")
                if(availableDate.isNotEmpty()) {
                    TextButton(onClick = {
                        openDialog = true
                    }) {
                        Text("Change date")
                    }
                }
            }

        }
    }
}

@Composable
fun LocationSelection(
    addressValue: String,
    countyValue: String,
    latitudeValue: String,
    longitudeValue: String,
    onAddressValueChanged: (newValue: String) -> Unit,
    onLatitudeValueChanged: (newValue: String) -> Unit,
    onLongitudeValueChanged: (newValue: String) -> Unit,
    onCountyValueChanged: (newValue: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Location",
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InputField(
                label = "County",
                value = countyValue,
                onValueChanged = onCountyValueChanged,
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            InputField(
                label = "Address",
                value = addressValue,
                onValueChanged = onAddressValueChanged,
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .weight(1f)
            )

        }
//        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InputField(
                label = "Latitude",
                value = latitudeValue,
                onValueChanged = onLatitudeValueChanged,
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            InputField(
                label = "Longitude",
                value = longitudeValue,
                onValueChanged = onLongitudeValueChanged,
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Composable
fun PropertyFeatures(
    modifier: Modifier = Modifier
) {
    var viewModel: CreateNewPropertyViewModel = viewModel()
    val uiState by viewModel.featuresInputFieldsUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        uiState.features.forEachIndexed { index, it ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                InputField(
                    keyboardType = KeyboardType.Text,
                    label = "Feature ${index + 1}",
                    value = it,
                    onValueChanged = {feature ->
                                     viewModel.features[index] = feature
                        viewModel.updateFeaturesFieldUiState();
                    },
                    modifier = Modifier
                        .weight(2f)
                )
                IconButton(
                    onClick = {
                        viewModel.removeFeatureField(index = index)
                    },
                    modifier = Modifier
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "Remove field"
                    )
                }
            }

        }
        IconButton(
            onClick = {
                viewModel.addNewField()
            },
            modifier = Modifier
                    .align(Alignment.End)
        )
        {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new feature"
            )
        }
    }
}

data class FeaturesInputField(
    val keyboardType: KeyboardType,
    val label: String,
    var value: String,
    val onValueChanged: (newValue: String) -> Unit,
)

@Preview(showBackground = true)
@Composable
fun PropertyFeaturesPreview() {
    PropertyManagementTheme {
        PropertyFeatures()
    }
}


@Preview(showBackground = true)
@Composable
fun LocationSelectionSelectionPreview() {
    PropertyManagementTheme {
        LocationSelection(
            addressValue = "",
            countyValue = "",
            latitudeValue = "",
            longitudeValue = "",
            onAddressValueChanged = {},
            onLatitudeValueChanged = {},
            onLongitudeValueChanged = {},
            onCountyValueChanged = {},
        )
    }
}




@Preview(showBackground = true)
@Composable
fun AvailabilitySelectionPreview() {
    PropertyManagementTheme {
        AvailabilitySelection()
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    PropertyManagementTheme {
        InputField(
            label = "Title",
            value = "",
            keyboardType = KeyboardType.Text,
            onValueChanged = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyTypeSelectionPreview() {
    PropertyManagementTheme {
        PropertyTypeSelection()
    }
}

@Preview(showBackground = true)
@Composable
fun CreateNewPropertyScreenPreview() {
    PropertyManagementTheme {
        CreateNewPropertyScreen()
    }
}
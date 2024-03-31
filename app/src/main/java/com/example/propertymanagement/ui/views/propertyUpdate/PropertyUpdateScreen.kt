package com.example.propertymanagement.ui.views.propertyUpdate

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.propertymanagement.R
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.example.propertymanagement.utils.convertMillisToDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object PropertyUpdateScreenDestination: NavigationDestination {
    override val route: String = "property-update"
    override val titleRes: Int = R.string.property_update_screen
    val propertyId: String = "propertyId"

    val routeWithArgs = "$route/{$propertyId}"
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertyUpdateScreen(
    navigateToUserAdvertisedProperties: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: PropertyUpdateScreenViewModel = viewModel(
        factory = AppViewModelFactory.Factory
    )
    val uiState by viewModel.uiState.collectAsState()
    var showPreview by rememberSaveable {
        mutableStateOf(false)
    }
    if(showPreview) {
        UpdatePropertyPreviewScreen(
            navigateToUserAdvertisedProperties = { navigateToUserAdvertisedProperties() },
            context = context,
            categoryId = uiState.generalPropertyDetails.categoryId,
            viewModel = viewModel,
            onBackButtonClicked = { showPreview = false })
    } else {
        Scaffold(
            topBar = {
                UpdatePropertyTopBar()
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 10.dp,
                                end = 10.dp
                            )
                    ) {
                        Text(
                            text = "Update your property",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = {
                            showPreview = true
                        }) {
                            Row {
                                Text(text = "Preview")
                                Icon(
                                    painter = painterResource(id = R.drawable.preview),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    // edit below

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Card() {
                            Column(
                                modifier = Modifier
                                    .padding(20.dp)
                            ) {
                                Row {
                                    PropertyTypeSelection(
                                        viewModel = viewModel,
                                        modifier = Modifier
                                            .weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    NumberOfRoomsSelection(
                                        viewModel = viewModel,
                                        modifier = Modifier
                                            .weight(1f)
                                    )
                                }
                                //title field
                                InputField(
                                    label = "Title",
                                    value = uiState.generalPropertyDetails.title,
                                    onValueChanged = {title ->

                                        viewModel.updatePropertyTitle(title = title)
                                    },
                                    keyboardType = KeyboardType.Text,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                //description field
                                InputField(
                                    label = "Description",
                                    value = uiState.generalPropertyDetails.description,
                                    onValueChanged = {description ->
                                        viewModel.updatePropertyDescription(description = description)
                                    },
                                    keyboardType = KeyboardType.Text,
                                    maxLines = 5,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                AvailabilitySelection(
                                    viewModel = viewModel
                                )
                                LocationSelection(
                                    addressValue = uiState.generalPropertyDetails.address,
                                    countyValue = uiState.generalPropertyDetails.county,
                                    latitudeValue = "",
                                    longitudeValue = "",
                                    onAddressValueChanged = {address ->
                                        viewModel.updatePropertyAddress(address = address)
                                    },
                                    onLatitudeValueChanged = {},
                                    onLongitudeValueChanged = {},
                                    onCountyValueChanged = {county ->
                                        viewModel.updatePropertyCounty(county)
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Price",
                                    fontWeight = FontWeight.Bold
                                )
                                InputField(
                                    label = "Price",
                                    value = uiState.generalPropertyDetails.price,
                                    onValueChanged = {price ->
                                        viewModel.updatePropertyPrice(price)
                                    },
                                    keyboardType = KeyboardType.Number,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Add features",
                                    fontWeight = FontWeight.Bold
                                )
                                PropertyFeatures(
                                    viewModel = viewModel
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Upload photos",
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(10.dp))
//                            ImagesUpload(
//                                context = context,
//                                viewModel = viewModel
//                            )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun PropertyTypeSelection(
    viewModel: PropertyUpdateScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val propertyTypes = uiState.categories
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
                Text(text = uiState.generalPropertyDetails.type)
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
                        text = { Text(text = type.name) },
                        onClick = {
                            selectedType = type.name
                            expanded = false
                            viewModel.updatePropertyCategory(
                                categoryId = type.id,
                                type = type.name
                            )
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
    viewModel: PropertyUpdateScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val numOfRooms = listOf<String>("1", "2", "3", "4", "5", "6")
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
                Text(text = uiState.generalPropertyDetails.rooms)
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
                            viewModel.updatePropertyRooms(
                                rooms = num
                            )
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
    maxLines: Int,
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
        maxLines = maxLines,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailabilitySelection(
    viewModel: PropertyUpdateScreenViewModel,
    modifier: Modifier = Modifier
) {
    val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val uiState by viewModel.uiState.collectAsState()
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
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val now = remember {
        LocalDate.now()
    }
    val formattedDate = now.format(dateFormatter)
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
            Text(text = "Available from: ${uiState.generalPropertyDetails.date}")
            viewModel.updateAvailabilityDate(date = formattedDate)

        } else {
            if(openDialog) {
                DatePickerDialog(
                    onDismissRequest = { openDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            openDialog = false
                            availableDate = selectedDate!!
                            val parsedDate = inputDateFormat.parse(availableDate)
                            val formattedDate = outputDateFormat.format(parsedDate)
                            viewModel.updateAvailabilityDate(date = formattedDate)

                            Log.i("FORMATTED_DATE", formattedDate)
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
                Text(text = "Available from: ${uiState.generalPropertyDetails.date}")
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
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            InputField(
                label = "Address",
                value = addressValue,
                onValueChanged = onAddressValueChanged,
                keyboardType = KeyboardType.Text,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
            )

        }

    }
}

@Composable
fun PropertyFeatures(
    viewModel: PropertyUpdateScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        uiState.generalPropertyDetails.features.forEachIndexed { index, it ->
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
                        viewModel.updateFeatureField(
                            index = index,
                            value = feature
                        )
                    },
                    maxLines = 1,
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

//@Composable
//fun ImagesUpload(
//    context: Context,
//    viewModel: PropertyUpdateScreenViewModel,
//    modifier: Modifier = Modifier
//) {
//
//
//    val uiState by viewModel.imagesUiState.collectAsState()
//
//    var imageUrl by remember {
//        mutableStateOf<Uri?>(null)
//    }
//    var images by remember {
//        mutableStateOf<List<Uri>>(listOf()) // Initialize images list as empty
//    }
//    val cacheDir = context.cacheDir
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent(),
//        onResult = { uri ->
//            uri?.let {
//                imageUrl = it
//                images = images.toMutableList().apply { add(imageUrl!!) } // Update images list
//
//                // Open an input stream from the content resolver associated with the URI
//                viewModel.images.add(uri)
//                viewModel.updateImagesUiState()
//            }
//        }
//    )
//
//
//
//
//    Column {
//        TextButton(onClick = { galleryLauncher.launch("image/*") }) {
//            Row {
//                Text(text = "Add images")
//                Icon(
//                    painter = painterResource(id = R.drawable.file_upload),
//                    contentDescription = "Add image"
//                )
//            }
//        }
//        Row(
//            modifier = Modifier
//                .horizontalScroll(rememberScrollState())
//        ) {
//            uiState.images.forEachIndexed { index, uri ->
//                Row {
//                    Card(
//                        modifier = Modifier
//                            .height(100.dp)
//                    ) {
//                        Image(
//                            rememberImagePainter(data = uri),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .padding(
//                                    top = 5.dp,
//                                    end = 3.dp,
//                                    bottom = 5.dp
//                                )
//                                .size(100.dp)
//                        )
//                    }
//                    IconButton(onClick = {
//                        viewModel.images.removeAt(index)
//                        viewModel.updateImagesUiState()
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = null
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun UpdatePropertyTopBar(
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 9.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            //        horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.prop_ease_3),
                contentDescription = null,
                modifier = Modifier
                    .width(90.dp)
                    .height(60.dp)
            )
            Text(
                text = "PropEase",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Update property",
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PropertyUpdateScreenPreview() {
    PropertyManagementTheme {
        PropertyUpdateScreen(
            navigateToUserAdvertisedProperties = {}
        )
    }
}
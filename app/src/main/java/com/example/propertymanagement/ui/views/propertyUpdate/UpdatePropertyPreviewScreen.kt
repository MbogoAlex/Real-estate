package com.example.propertymanagement.ui.views.propertyUpdate

import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.propertymanagement.R
import com.example.propertymanagement.apiServices.model.Image
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.example.propertymanagement.ui.views.CreateNewPropertyViewModel
import com.example.propertymanagement.ui.views.FeaturesInputFieldsUiState
import com.example.propertymanagement.ui.views.GeneralPropertyDataUiState
import com.example.propertymanagement.ui.views.GeneralPropertyDetails
import com.example.propertymanagement.ui.views.ImagesUiState
import com.example.propertymanagement.ui.views.UploadStatus
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdatePropertyPreviewScreen(
    navigateToUserAdvertisedProperties: () -> Unit,
    context: Context,
    categoryId: Int,
    viewModel: PropertyUpdateScreenViewModel,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()


    if(uiState.updateStatus == UpdateStatus.DONE) {
        navigateToUserAdvertisedProperties()
        Toast.makeText(LocalContext.current, "Property updated", Toast.LENGTH_SHORT).show()
        viewModel.stopLoadingToPreviousPage()

    } else if (uiState.updateStatus == UpdateStatus.FAIL) {
        Toast.makeText(LocalContext.current, "Failed to update property", Toast.LENGTH_SHORT).show()
        viewModel.initializeStatus()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        UpdatePropertyPreviewTopBar(
            uiState = uiState,
            onBackButtonClicked = onBackButtonClicked,
            onSaveButtonClicked = {
                Toast.makeText(context, "This feature will be added soon", Toast.LENGTH_SHORT).show()
//                viewModel.updateProperty(
////                    categoryId = categoryId,
////                    context = context
//                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            ImageSlider(
                images = uiState.generalPropertyDetails.images
            )
            UpdatedPropertyDetails(
                uiState = uiState,
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    images: List<Image>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column {
        Card {
            HorizontalPager(count = images.size, state = pagerState) { page ->
                Image(
                    painter = rememberImagePainter(data = images[page].url),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}

@Composable
fun UpdatedPropertyDetails(
    uiState: PropertyUpdateUiState,
    modifier: Modifier = Modifier
) {
    Column {
        Row {
            Text(
                text = "${uiState.generalPropertyDetails.type}: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "${uiState.generalPropertyDetails.rooms} rooms",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${uiState.generalPropertyDetails.county}, ${uiState.generalPropertyDetails.address}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Available from, ${uiState.generalPropertyDetails.date}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = uiState.generalPropertyDetails.title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = uiState.generalPropertyDetails.description,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Features:",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        uiState.generalPropertyDetails.features.forEachIndexed { index, feature ->
            Text(text = "${index + 1}. $feature")
        }
    }
}

@Composable
fun UpdatePropertyPreviewTopBar(
    uiState: PropertyUpdateUiState,
    onBackButtonClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = { onBackButtonClicked() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        Text(text = "Preview")
        if(uiState.updateStatus == UpdateStatus.START) {
            CircularProgressIndicator()
        } else {
            TextButton(onClick = { onSaveButtonClicked() }) {
                Row {
                    Text(text = "Update")
                    Icon(
                        painter = painterResource(id = R.drawable.save),
                        contentDescription = null
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CreatePropertyPreviewTopBarPreview() {
    PropertyManagementTheme {
        UpdatePropertyPreviewTopBar(
            uiState = PropertyUpdateUiState(),
            onBackButtonClicked = {},
            onSaveButtonClicked = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CreatePropertyPreviewScreenPreview() {
    val context = LocalContext.current
    val viewModel: PropertyUpdateScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val generalPropertyDetails = GeneralPropertyDetails(
        type = "Arbnb",
        rooms = "2",
        title = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        description = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
        date = "17-04-2024",
        county = "Kiambu",
        address  = "Kiambu - road",
        latitude = "",
        longitude = "",
        price = "15,000",
        features = mutableListOf("Wi-Fi", "Security", "24/7 water"),
        images = mutableListOf(),
    )
    PropertyManagementTheme {
        UpdatePropertyPreviewScreen(
            context = context,
            categoryId = 1,
            viewModel = viewModel,
            onBackButtonClicked = {},
            navigateToUserAdvertisedProperties = {}
        )
    }
}
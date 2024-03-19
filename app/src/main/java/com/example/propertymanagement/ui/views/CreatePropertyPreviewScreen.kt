package com.example.propertymanagement.ui.views

import android.content.Context
import android.net.Uri
import android.os.Build
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreatePropertyPreviewScreen(
    context: Context,
    categoryId: Int,
    viewModel: CreateNewPropertyViewModel,
    generalPropertyDetails: GeneralPropertyDetails,
    featuresInputFieldsUiState: FeaturesInputFieldsUiState,
    imagesUiState: ImagesUiState,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        CreatePropertyPreviewTopBar(
            onBackButtonClicked = onBackButtonClicked,
            onSaveButtonClicked = {
                viewModel.uploadProperty(
                    categoryId = categoryId,
                    context = context
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            ImageSlider(
                generalPropertyDetails = generalPropertyDetails,
                images = imagesUiState.images
            )
            NewPropertyDetails(
                featuresInputFieldsUiState = featuresInputFieldsUiState,
                generalPropertyDetails = generalPropertyDetails
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    generalPropertyDetails: GeneralPropertyDetails,
    images: List<File>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column {
        Card {
            HorizontalPager(count = images.size, state = pagerState) { page ->
                Image(
                    painter = rememberImagePainter(data = images[page]),
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
fun NewPropertyDetails(
    featuresInputFieldsUiState: FeaturesInputFieldsUiState,
    generalPropertyDetails: GeneralPropertyDetails,
    modifier: Modifier = Modifier
) {
    Column {
        Row {
            Text(
                text = "${generalPropertyDetails.type}: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "${generalPropertyDetails.rooms} rooms",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${generalPropertyDetails.county}, ${generalPropertyDetails.address}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Available from, ${generalPropertyDetails.date}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = generalPropertyDetails.title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = generalPropertyDetails.description,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Features:",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        featuresInputFieldsUiState.features.forEachIndexed { index, feature ->
            Text(text = "${index + 1}. $feature")
        }
    }
}

@Composable
fun CreatePropertyPreviewTopBar(
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
        TextButton(onClick = { onSaveButtonClicked() }) {
            Row {
                Text(text = "Publish")
                Icon(
                    painter = painterResource(id = R.drawable.save),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePropertyPreviewTopBarPreview() {
    PropertyManagementTheme {
        CreatePropertyPreviewTopBar(
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
    val viewModel: CreateNewPropertyViewModel = viewModel(factory = AppViewModelFactory.Factory)
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
        CreatePropertyPreviewScreen(
            context = context,
            categoryId = 1,
            viewModel = viewModel,
            generalPropertyDetails = generalPropertyDetails,
            featuresInputFieldsUiState = FeaturesInputFieldsUiState(),
            imagesUiState = ImagesUiState(),
            onBackButtonClicked = {}
        )
    }
}
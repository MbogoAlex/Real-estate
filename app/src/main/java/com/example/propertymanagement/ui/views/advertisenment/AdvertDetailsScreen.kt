package com.example.propertymanagement.ui.views.advertisenment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.propertymanagement.R
import com.example.propertymanagement.apiServices.model.Image
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

object UserAdvertDetailsScreenDestination: NavigationDestination {
    override val route: String = "advertDetails"
    override val titleRes: Int = R.string.advert_details_screen
    val unitId: String = "unitId"
    val routeWithArgs: String = "$route/{$unitId}"
}
@Composable
fun AdvertDetailsScreen(
    navigateToEditProperty: (propertyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: AdvertDetailsScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            AdvertDetailsScreenTopBar()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Row(
//                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        navigateToEditProperty(uiState.id)
                    }) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = null
                            )
                            Text(text = "Edit property")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
//                    Spacer(modifier = Modifier.height(20.dp))
                    AdvertImagesSlider(

                        images = uiState.images
                    )
                    AdvertPropertyDetails(
                        uiState = uiState
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdvertImagesSlider(
    images: List<Image>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column {
        Card {
            HorizontalPager(count = images.size, state = pagerState) { page ->
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(images[page].url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
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
fun AdvertPropertyDetails(
    uiState: AdvertDetailsScreenUiState,
    modifier: Modifier = Modifier
) {
    Column {
        Row {
//            Text(
//                text = "${uiState.type}: ",
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp
//            )
            Text(
                text = "${uiState.rooms} room(s)",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${uiState.location.county}, ${uiState.location.address}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Available from, ${uiState.availableDate}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = uiState.title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = uiState.description,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Features:",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        uiState.features.forEachIndexed { index, feature ->
            Text(text = "${index + 1}. $feature")
        }
    }
}

@Composable
fun AdvertDetailsScreenTopBar(
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
                text = "Live property",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvertDetailsScreenPreview(
    modifier: Modifier = Modifier
) {
    PropertyManagementTheme {
        AdvertDetailsScreen(
            navigateToEditProperty = {}
        )
    }
}

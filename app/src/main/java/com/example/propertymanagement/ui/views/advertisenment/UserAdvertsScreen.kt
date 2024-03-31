package com.example.propertymanagement.ui.views.advertisenment

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.propertymanagement.R
import com.example.propertymanagement.apiServices.model.UserPropertyDataProperty
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.theme.PropertyManagementTheme


object UserAdvertsScreenDestination: NavigationDestination {
    override val route: String = "my-units-adverts"
    override val titleRes: Int = R.string.create_property_screen
}
@Composable
fun UserAdvertsScreen(
    navigateToCreatePropertyScreen: () -> Unit,
    navigateToAdvertDetails: (unitId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: UserAdvertsViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
                 AdvertsScreenTopBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToCreatePropertyScreen() }) {
                Icon(
                    painter = painterResource(id = R.drawable.upload_property),
                    contentDescription = null
                )
            }
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if(uiState.listingsData.listings.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(text = "You have no advertised properties")
                    }
                } else {
                    ScrollableUnitsScreen(
                        isRegistered = uiState.userDetails.userId != null,
                        uiState = uiState,
                        navigateToAdvertDetails = navigateToAdvertDetails,
                        showContact = { /*TODO*/ }
                    )
                }

            }
        }
    }
}

@Composable
fun ScrollableUnitsScreen(
    navigateToAdvertDetails: (unitId: String) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    uiState: UserAdvertisementsUiState,
    showContact: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    val listings = uiState.listingsData.listings.reversed()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(listings) {
            UnitItem(
                navigateToAdvertDetails = navigateToAdvertDetails,
                isRegistered = isRegistered,
                unit = it,
                showContact = showContact,
                modifier = Modifier
//                    .padding(
//                        top = 10.dp
//                    )
            )
        }

    }
}

@Composable
fun UnitItem(
    navigateToAdvertDetails: (unitId: String) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    unit: UserPropertyDataProperty,
    showContact: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .padding(
                start = 10.dp,
//                    top = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            )
            .clickable { navigateToAdvertDetails(unit.propertyId.toString()) }
    ) {
        if(unit.images.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(unit.images.first().url)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                contentDescription = unit.title,
                modifier = Modifier
                    .height(250.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.no_image_icon_coming_soon),
                contentDescription = "No image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = unit.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier

        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                )
                Text(
                    text = "${unit.location.county}, ${unit.location.address}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navigateToAdvertDetails(unit.propertyId.toString()) }
//                        .align(Alignment.End)
            )
        }
//            Spacer(modifier = Modifier.height(10.dp))
        if(!isRegistered) {
            Button(
                onClick = {
                    showContact()

                },
                modifier = Modifier

            ) {
                Text(
                    text = "Show Contact",
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
//            Spacer(modifier = Modifier.height(10.dp))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertsScreenTopBar(
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
                text = "Your Properties",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserAdvertsScreenPreview() {
    PropertyManagementTheme {
        UserAdvertsScreen(
            navigateToCreatePropertyScreen = {},
            navigateToAdvertDetails = {}
        )
    }
}
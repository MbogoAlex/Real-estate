package com.example.propertymanagement.ui.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.propertymanagement.R
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.PropertyUnit
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

object UnitDetailsScreenDestination: NavigationDestination {
    override val route: String = "unitDetails"
    override val titleRes: Int = R.string.unit_details_screen
    val unitId: String = "unitId"
    val routeWithArgs: String = "$route/{$unitId}"
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun UnitDetailsScreen(
//    unit: PropertyUnit,
//    isRegistered: Boolean,
    onBackButtonPressed: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: UnitsDetailsScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0)

    BackHandler {
        onBackButtonPressed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        UnitDetailsHeader(
            onBackButtonPressed = { onBackButtonPressed() },
            modifier = Modifier
                .fillMaxWidth()
        )
        UnitDetails(
            pagerState = pagerState,
            uiState = uiState
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UnitDetails(
    pagerState: PagerState,
    uiState: UnitDetailsUiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {

        Card {
            HorizontalPager(count = uiState.propertyUnit.images.size, state = pagerState) { page ->
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(uiState.propertyUnit.images[page].url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                    contentDescription = uiState.propertyUnit.title,
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
        Row {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Text(
                text = "${uiState.propertyUnit.location.county}, ${uiState.propertyUnit.location.address}"
            )
        }

        Text(
            text = uiState.propertyUnit.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = uiState.propertyUnit.description,
            fontSize = 16.sp,

            )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Rooms: ${uiState.propertyUnit.rooms}")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Features:",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        uiState.propertyUnit.features.forEach {
            Text(text = it)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Seller:",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "${uiState.propertyUnit.seller.fname} ${uiState.propertyUnit.seller.lname}")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.propertyUnit.seller.phoneNumber
            )
            TextButton(onClick = {
                val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${uiState.propertyUnit.seller.phoneNumber}")
                }
                context.startActivity(phoneIntent)
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        tint = Color.Blue,
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                    )
                    Text(
                        text = "Call",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            color = Color.Blue
                        ),
                        modifier = Modifier
                            .padding(
                                bottom = 5.dp
                            )
                    )
                }
            }
        }
//        Text(
//            text = "Seller: ${uiState.propertyUnit.seller.name}",
//            fontWeight = FontWeight.Bold
//        )
        Spacer(modifier = Modifier.height(10.dp))
//        if(uiState.userRegistered) {
//            Text(
//                text = uiState.propertyUnit.seller.phoneNumber,
//                modifier = Modifier
////                    .align(Alignment.End)
//            )
//        } else {
//            Button(
//                onClick = { /*TODO*/ },
//                modifier = Modifier
//
//            ) {
//                Text(
//                    text = "Show Contact",
//                )
//            }
//        }
    }
}
@Composable
fun UnitDetailsHeader(
    onBackButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 9.dp,
        modifier = modifier
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
            IconButton(
                onClick = { onBackButtonPressed() },
                modifier = Modifier
                    .padding(
                        bottom = 10.dp
                    )

            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
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
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitDetailsCompactPreview(
    modifier: Modifier = Modifier
) {
    PropertyManagementTheme {
        UnitDetailsScreen(

            onBackButtonPressed = {},

        )
    }
}
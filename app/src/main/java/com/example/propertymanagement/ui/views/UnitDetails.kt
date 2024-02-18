package com.example.propertymanagement.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.Unit
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UnitDetails(
    unit: Unit,
    isRegistered: Boolean,
    onBackButtonPressed: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)

    BackHandler {
        onBackButtonPressed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(ScrollState(0))
    ) {
        Row {
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
        }
        Card {
            HorizontalPager(count = unit.images.size, state = pagerState) { page ->
                Image(
                    painter = painterResource(id = unit.images[page].image),
                    contentDescription = stringResource(id = unit.name),
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
        Row {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Text(
                text = stringResource(id = unit.location)
            )
        }

        Text(
            text = stringResource(id = unit.name),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = unit.description),
            fontSize = 16.sp,

        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Seller: ${unit.seller.name}",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        if(isRegistered) {
            Text(
                text = unit.seller.phoneNumber,
                modifier = Modifier
                    .align(Alignment.End)
            )
        } else {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier

            ) {
                Text(
                    text = "Show Contact",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitDetailsCompactPreview(
    modifier: Modifier = Modifier
) {
    PropertyManagementTheme {
        UnitDetails(
            isRegistered = false,
            onBackButtonPressed = {},
            unit = Datasource.units.first { it.id == 1 }
        )
    }
}
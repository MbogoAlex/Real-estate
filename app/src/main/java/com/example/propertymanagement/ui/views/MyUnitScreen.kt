package com.example.propertymanagement.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propertymanagement.R
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun MyUnitScreen(
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val myUnit = Datasource.units[0]
    var currentTab by remember {
        mutableStateOf(MyUnitTab.MY_UNIT)
    }
    var headerText by remember {
        mutableStateOf("My Units")
    }

    val myUnitNavContentList = listOf<MyUnitNavContent>(
        MyUnitNavContent(
            "About",
            MyUnitTab.MY_UNIT,
            painterResource(id = R.drawable.unit),
            "My Unit"
        ),
        MyUnitNavContent(
            "Invoice",
            MyUnitTab.INVOICE,
            painterResource(id = R.drawable.invoice_tab),
            "Invoice"
        ),
        MyUnitNavContent(
            "Payments",
            MyUnitTab.PAYMENTS,
            painterResource(id = R.drawable.payments_tab),
            "Payments"
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        MyUnitHeader(headerText = headerText)
        Spacer(modifier = Modifier.height(10.dp))
        FilterMyUnitsBoxes()
        Spacer(modifier = Modifier.height(10.dp))

        MyUnitScreenMenu(changeTab = {
            currentTab = it
        })
        Spacer(modifier = Modifier.height(20.dp))
        when (currentTab) {
            MyUnitTab.MY_UNIT -> {
                MyUnitDetails(
                    unit = myUnit
                )
                headerText = "My Units"
            }
            MyUnitTab.INVOICE -> {
                InvoiceScreen()
                headerText = "Invoices"
            }
            MyUnitTab.PAYMENTS -> {
                PaymentsScreen()
                headerText = "Payments"
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MyUnitDetails(
    unit: com.example.propertymanagement.model.PropertyUnit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column(

        modifier = modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0))


    ) {

        Card(
            modifier = Modifier
                .padding(10.dp)
        ) {
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
        Text(
            text = stringResource(id = unit.name),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 10.dp
                )

        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = unit.description),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 10.dp
                )
        )
    }
}

@Composable
fun MyUnitHeader(
    headerText: String,
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
                text = headerText,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MyUnitScreenMenu(
    changeTab: (myUnitTab: MyUnitTab) -> Unit,
    modifier: Modifier = Modifier
) {
    var myUnitClicked by remember {
        mutableStateOf(true)
    }
    var invoiceClicked by remember {
        mutableStateOf(false)
    }
    var paymentsClicked by remember {
        mutableStateOf(false)
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //My Unit Tab

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(80.dp)
                .clickable {
                    myUnitClicked = true
                    invoiceClicked = false
                    paymentsClicked = false
                    changeTab(MyUnitTab.MY_UNIT)
                }

        ) {
            Text(
                text = "About",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
//                        top = 20.dp,
                        bottom = 10.dp
                    )

            )
            if(myUnitClicked) {
                Divider(
                    thickness = 5.dp,
                )
            }
        }

        //Invoice Tab
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(80.dp)
                .clickable {
                    myUnitClicked = false
                    invoiceClicked = true
                    paymentsClicked = false
                    changeTab(MyUnitTab.INVOICE)
                }
        ) {
            Text(
                text = "Invoice",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
//                        top = 20.dp,
                        bottom = 10.dp
                    )
            )
            if(invoiceClicked) {
                Divider(
                    thickness = 5.dp,
                )
            }
        }

        //Payments Tab
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(80.dp)
                .clickable {
                    myUnitClicked = false
                    invoiceClicked = false
                    paymentsClicked = true
                    changeTab(MyUnitTab.PAYMENTS)
                }
        ) {
            Text(
                text = "Payments",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
//                        top = 20.dp,
                        bottom = 10.dp
                    )
            )
            if(paymentsClicked) {
                Divider(
                    thickness = 5.dp,
                )
            }
        }
    }
}

@Composable
fun FilterMyUnitsBoxes(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                top = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            )

    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray
            ),
            modifier = Modifier
                .padding(
                    end = 5.dp,
                )


        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lavendar",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .widthIn(min = 90.dp)
                        .padding(10.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select number of rooms"
                )
            }
        }
        Spacer(modifier = Modifier.width(5.dp))

        Divider(
            thickness = 5.dp,
            modifier = Modifier
                .width(1.dp)
                .height(40.dp)
        )

        //Scrollable boxes
        Spacer(modifier = Modifier.width(5.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(ScrollState(0))
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Cyan
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                    )
            ) {
                Text(
                    text = "Rentals",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .widthIn(min = 90.dp)
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                    )

            ) {
                Text(
                    text = "Airbnbs",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .widthIn(min = 90.dp)
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                    )
            ) {
                Text(
                    text = "Airbnbs",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .widthIn(min = 90.dp)
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                    )
            ) {
                Text(
                    text = "Airbnbs",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .widthIn(min = 90.dp)
                        .padding(10.dp)
                )
            }
        }

    }
}

enum class MyUnitTab {
    MY_UNIT,
    INVOICE,
    PAYMENTS
}

private data class MyUnitNavContent(
    val name: String,
    val tab: MyUnitTab,
    val painter: Painter,
    val contentDescription: String
)

@Preview(showBackground = true)
@Composable
fun MyUnitScreenMenuPreview() {
    PropertyManagementTheme {
        MyUnitScreenMenu(
            changeTab = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyUnitScreenPreview() {
    PropertyManagementTheme {
        MyUnitScreen(
            onBackButtonClicked = {}
        )
    }
}
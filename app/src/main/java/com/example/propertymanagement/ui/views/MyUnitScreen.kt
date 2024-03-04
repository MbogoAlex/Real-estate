package com.example.propertymanagement.ui.views

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
    modifier: Modifier = Modifier
) {
    val myUnit = Datasource.units[0]
    var currentTab by remember {
        mutableStateOf(MyUnitTab.MY_UNIT)
    }
    val myUnitNavContentList = listOf<MyUnitNavContent>(
        MyUnitNavContent(
            "My Unit",
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

    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        SideNavigationRail(
            myUnitNavContentList = myUnitNavContentList,
            myUnitTab = currentTab,
            onTabClicked = { currentTab = it },
            modifier = Modifier
                .padding(
//                    top = 10.dp,
//                    end = 5.dp
                )

        )
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
//
        when (currentTab) {
            MyUnitTab.MY_UNIT -> {
                MyUnitDetails(unit = myUnit)
            }
            MyUnitTab.INVOICE -> {
                InvoiceScreen()
            }
            MyUnitTab.PAYMENTS -> {
                PaymentsScreen()
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyUnitDetails(
    unit: com.example.propertymanagement.model.Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column(

        modifier = modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0))


    ) {
        MyUnitHeader()
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
private fun SideNavigationRail(
    myUnitNavContentList: List<MyUnitNavContent>,
    myUnitTab: MyUnitTab,
    onTabClicked: (tab: MyUnitTab) -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
            .width(70.dp)

            .padding(
                top = 10.dp
            )
    ) {
        for (navItem in myUnitNavContentList) {
            NavigationRailItem(
                selected = navItem.tab == myUnitTab,
                onClick = { onTabClicked(navItem.tab) },
                icon = {
                    Icon(
                        painter = navItem.painter,
                        contentDescription = navItem.contentDescription
                    )
                },
                label = {
                    Text(text = navItem.name)
                }
            )
        }
    }
}

@Composable
fun MyUnitHeader(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home icon"
        )
        Text(
            text = "PropEase",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,

            )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
        Text(
            text = "My Unit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,

            )
    }
}

private enum class MyUnitTab {
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
fun MyUnitScreenPreview() {
    PropertyManagementTheme {
        MyUnitScreen()
    }
}
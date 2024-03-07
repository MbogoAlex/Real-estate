package com.example.propertymanagement.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertymanagement.R
import com.example.propertymanagement.datasource.Datasource
import com.example.propertymanagement.model.NavigationContent
import com.example.propertymanagement.model.Tab
import com.example.propertymanagement.model.Unit
import com.example.propertymanagement.model.UnitType
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.appViewModel.AppViewModel
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.state.AppUiState
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlin.jvm.internal.Intrinsics.Kotlin

object HomeDestination: NavigationDestination {
    override val route: String = "Home"
    override val titleRes: Int = R.string.units_screen
}
@Composable
fun PropertyScreen(
    showSelectedUnit: (unitId: Int) -> kotlin.Unit = {id -> },
    navigateToRegistrationPage: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: AppViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    var showRegisterUserAlert by remember {
        mutableStateOf(false)
    }

    if(showRegisterUserAlert) {
        RegisterUserAlert(
            onConfirmRequest = navigateToRegistrationPage,
            onDismissRequest = {
                showRegisterUserAlert = false
            }
        )
    }

    val navigationContentList = listOf<NavigationContent>(
        NavigationContent(
            title = "Listing",
            icon = painterResource(id = R.drawable.units_listing),
            currentTab = Tab.UNITS
        ),
        NavigationContent(
            title = "My Units",
            icon = painterResource(id = R.drawable.unit),
            currentTab = Tab.MY_UNITS,

            ),
        NavigationContent(
            title = "Notifications",
            icon = painterResource(id = R.drawable.notifications),
            currentTab = Tab.NOTIFICATIONS,

            ),
        NavigationContent(
            title = "Profile",
            icon = painterResource(id = R.drawable.account_tab),
            currentTab = Tab.ACCOUNT
        ),

        )

    Column {
        when(uiState.currentTab) {
            Tab.UNITS -> {
                UnitsScreen(
                    uiState = uiState,
                    onBackButtonPressed = { viewModel.onBackButtonClicked() },
                    showSelectedUnit = { viewModel.showUnitDetails(it) },
                    onTabClicked = { currentTab ->  },
                    filterUnits = { viewModel.filterUnits(it) },
                    showContact = {
                        viewModel.checkIfRegistered()
                        if(!uiState.isRegistered) {
                            showRegisterUserAlert = true
                        }
                                  },
                    modifier = Modifier
                        .weight(1f)

                )
            }
            Tab.MY_UNITS -> {
                MyUnitScreen(
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Tab.NOTIFICATIONS -> {
                NotificationsScreen(
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Tab.ACCOUNT -> {
                AccountScreen(
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }


        BottomNavigationBar(
            navigationContentList = navigationContentList,
            currentTab = uiState.currentTab,
            onTabClicked = { currentTab ->  viewModel.switchTab(currentTab)},
        )
    }



}

@Composable
fun UnitsScreen(
    uiState: AppUiState,
    onBackButtonPressed: () -> kotlin.Unit,
    showSelectedUnit: (unitId: Int) -> kotlin.Unit,
    onTabClicked: (currentTab: Tab) -> kotlin.Unit,
    filterUnits: (type: UnitType) -> kotlin.Unit,
    showContact: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val items = UnitType.values()
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    var location by remember {
        mutableStateOf("")
    }



    if(uiState.showUnitDetails) {

        UnitDetails(
            unit = uiState.selectedUnit,
            isRegistered = false,
            onBackButtonPressed = onBackButtonPressed
        )
    } else {
        Column(
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                if(uiState.showFilteredUnits) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                            modifier = Modifier
//                                .padding(
//                                    top = 13.dp
//                                )
                                .size(25.dp)
                        )
                        Text(
                            text = items[selectedIndex].label,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .clickable { expanded = true }

                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier

                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                            modifier = Modifier
//                                .padding(
//                                    top = 13.dp
//                                )
                                .size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Select type",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .clickable { expanded = true }
//                                .padding(
//                                    top = 20.dp
//                                )
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier

                        )
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = { Text(text = item.label) },
                            onClick = {
                                selectedIndex = index
                                filterUnits(items[selectedIndex])
                                expanded = false
                            }

                        )
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = { Text(text = item.label) },
                            onClick = {
                                selectedIndex = index
                                filterUnits(items[selectedIndex])
                                expanded = false
                            }

                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    BasicTextField(
                        value = location,
                        onValueChange = {
                                        location = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        decorationBox = { innerTextField ->
                                        if(location.isEmpty()) {
                                            Text(
                                                text = "Search location",
                                                style = TextStyle(color = Color.Gray),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                            innerTextField()

                        },
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                end = 20.dp
                            )
                            .size(30.dp)
                    )
                }
            }

            ScrollableUnitsScreen(
                showSelectedUnit = showSelectedUnit,
                isRegistered = false,
                appUiState = uiState,
                showContact = showContact,
                modifier = Modifier
                    .weight(1f)

//                    .padding(10.dp)
            )


        }

    }
}

@Composable
fun ScrollableUnitsScreen(
    showSelectedUnit: (unitId: Int) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    appUiState: AppUiState,
    showContact: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(appUiState.unitsToDisplay.size) { index ->
            UnitItem(
                showSelectedUnit = showSelectedUnit,
                isRegistered = appUiState.isRegistered,
                unit = appUiState.unitsToDisplay[index],
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
    showSelectedUnit: (unitId: Int) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    unit: Unit,
    showContact: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {

        Column(
            modifier = Modifier
                .padding(
                    10.dp
                )
                .clickable { showSelectedUnit(unit.id) }
        ) {
            Image(
                painter = painterResource(id = unit.images.first { it.id == 1 }.image),
                contentDescription = stringResource(id = unit.name),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(250.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = unit.name),
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
                        text = stringResource(id = unit.location),
                        style = MaterialTheme.typography.labelLarge

                        )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { showSelectedUnit(unit.id) }
//                        .align(Alignment.End)
                )
            }
//            Spacer(modifier = Modifier.height(10.dp))
            if(isRegistered) {
                Text(
                    text = unit.seller.phoneNumber,
                    modifier = Modifier

                )
            } else {
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

@Composable
fun BottomNavigationBar(
    navigationContentList: List<NavigationContent>,
    currentTab: Tab,
    onTabClicked: (currentTab: Tab) -> kotlin.Unit,
    modifier: Modifier = Modifier,
) {

//    BottomNavigation(
//        backgroundColor = NavigationBarDefaults.containerColor,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        for(navItem in navigationContentList) {
//            BottomNavigationItem(
//                selected = navItem.currentTab == currentTab,
//                onClick = { onTabClicked(navItem.currentTab) },
//                label = { Text(text = navItem.title) },
//                selectedContentColor = Color.Red,
//                icon = {
//                    Icon(
//                        painter = navItem.icon,
//                        contentDescription = null
//                    )
//                }
//            )
//        }
//    }

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
    ) {
        for(navItem in navigationContentList) {
            NavigationBarItem(
                selected = navItem.currentTab == currentTab,
                onClick = { onTabClicked(navItem.currentTab) },
                label = { Text(text = navItem.title) },
                icon = {
                    Icon(
                        painter = navItem.icon,
                        contentDescription = null
                    )
                },

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(

        title = {
            Row {
                Text(
                    text = "SHELTER",
                    style = MaterialTheme.typography.displaySmall,
                )
            }
        },

    )
}

@Composable
fun RegisterUserAlert(
    onDismissRequest: () -> kotlin.Unit,
    onConfirmRequest: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { 
            onDismissRequest()
        }, 
        confirmButton = { 
                        TextButton(onClick = { onConfirmRequest() }) {
                            Text(text = "Sign in")
                        }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Dismiss")
            }
        },
        title = {
            Text(text = "Cannot show contact")
        },
        text = {
            Text(text = "Sign in in order to show contact")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ScrollableUnitsScreenCompactPreview(
    modifier: Modifier = Modifier
) {
    PropertyManagementTheme {
        PropertyScreen(
            navigateToRegistrationPage = {}
        )
    }
}


package com.example.propertymanagement.ui.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertymanagement.R
import com.example.propertymanagement.model.NavigationContent
import com.example.propertymanagement.model.BottomTab
import com.example.propertymanagement.model.PropertyUnit
import com.example.propertymanagement.model.UnitType
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.state.AppUiState
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

object HomeDestination: NavigationDestination {
    override val route: String = "Home"
    override val titleRes: Int = R.string.units_screen
}
@Composable
fun PropertyScreen(
    navigateToUnit: (unitId: String) -> kotlin.Unit = {id -> },
    onBackButtonPressed: () -> Unit,
    navigateToRegistrationPage: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {


    val viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val listingsUiState by viewModel.listingsUiState.collectAsState()
    val bottomBarUiState by viewModel.bottomBarUiState.collectAsState()


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
            title = "Listings",
            icon = painterResource(id = R.drawable.units_listing),
            currentTab = BottomTab.UNITS
        ),
        NavigationContent(
            title = "My Units",
            icon = painterResource(id = R.drawable.unit),
            currentTab = BottomTab.MY_UNITS,

            ),
        NavigationContent(
            title = "Notifications",
            icon = painterResource(id = R.drawable.notifications),
            currentTab = BottomTab.NOTIFICATIONS,

            ),
        NavigationContent(
            title = "Profile",
            icon = painterResource(id = R.drawable.account_tab),
            currentTab = BottomTab.ACCOUNT
        ),

        )

    Column {
        when(bottomBarUiState.bottomTab) {
            BottomTab.UNITS -> {
                UnitsScreen(
                    listingsUiState = listingsUiState,
//                    onBackButtonPressed = { viewModel.onBackButtonClicked() },
//                    showSelectedUnit = { viewModel.showUnitDetails(it) },
                    onTabClicked = { currentTab ->  },
                    filterUnits = { viewModel.filterUnits(
                        ListingsType.RENTALS,
                        PropertyRooms.ONE
                        ) },
                    showContact = {
                        if(viewModel.userRegistered) {
                            showRegisterUserAlert = false
                        } else {
                            showRegisterUserAlert = true
                        }
                    },
                    navigateToUnit = {
                        navigateToUnit(it.toString())
                    },
                    isRegistered = viewModel.userRegistered,
                    modifier = Modifier
                        .weight(1f)

                )
            }
            BottomTab.MY_UNITS -> {
                MyUnitScreen(
                    onBackButtonClicked = {},
                    modifier = Modifier
                        .weight(1f)
                )
            }
            BottomTab.NOTIFICATIONS -> {
                NotificationsScreen(
                    modifier = Modifier
                        .weight(1f)
                )
            }

            BottomTab.ACCOUNT -> {
                AccountScreen(
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
        BottomNavigationBar(
            navigationContentList = navigationContentList,
            currentTab = bottomBarUiState.bottomTab,
            newNotification = true,
            numberOfNotifications = 3,
            onTabClicked = { currentTab ->  viewModel.switchTab(currentTab)},
        )
    }



}

@Composable
fun UnitsScreen(
    listingsUiState: ListingsUiState,
    navigateToUnit: (unitId: Int) -> Unit,
//    onBackButtonPressed: () -> kotlin.Unit,
//    showSelectedUnit: (unitId: Int) -> kotlin.Unit,
    onTabClicked: (currentTab: BottomTab) -> kotlin.Unit,
    filterUnits: (type: UnitType) -> kotlin.Unit,
    isRegistered: Boolean,
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
     var showSearchField by remember {
         mutableStateOf(false)
     }


    Column(
        modifier = modifier
    ) {
        TopMostBar(
            searchLocation = "",
            onSearchIconClicked = {
                showSearchField = true
            },
            onStopSearchClicked = {
                showSearchField = false
            },
            showSearchField = showSearchField,
        )

        ScrollableUnitsScreen(
            navigateToUnit = navigateToUnit,
//            showSelectedUnit = showSelectedUnit,
            isRegistered = isRegistered,
            listingsUiState = listingsUiState,
            showContact = showContact,
            modifier = Modifier
                .weight(1f)

//                    .padding(10.dp)
        )
    }
}

@Composable
fun TopMostBar(
    searchLocation: String,
    onSearchIconClicked: () -> kotlin.Unit,
    onStopSearchClicked: () -> kotlin.Unit,
    showSearchField: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
    ) {
        if(showSearchField) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SearchField(
                    searchLocation = searchLocation,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = "Cancel searching",
                    modifier = Modifier
                        .padding(
                            end = 10.dp
                        )
                        .clickable {
                            onStopSearchClicked()
                        }
                )
            }
        } else {
            TopBarTextAndImage(
                onSearchIconClicked = { onSearchIconClicked() }
            )
        }
        FilterBoxes()
    }
}

@Composable
fun TopBarTextAndImage(
    onSearchIconClicked: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        elevation = 9.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 2.dp,
                bottom = 2.dp
            )
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
            Image(
                painter = painterResource(id = R.drawable.prop_ease_3),
                contentDescription = "App Icon",
                modifier = Modifier
                    .width(90.dp)
                    .height(60.dp)
            )
            Text(
                text = "PropEase",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onSearchIconClicked()
                    }
            )

        }
    }
}

@Composable
fun SearchField(
    searchLocation: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                top = 10.dp,
                end = 10.dp
            )
    ) {
        TextField(
            value = "",
            placeholder = {
                          Text(
                              text = "Search location",
                              fontWeight = FontWeight.Light
                          )
            },
            onValueChange = {},
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun FilterBoxes(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                top = 10.dp,
                end = 10.dp,
                bottom = 15.dp
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
                    text = "1 room",
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

@Composable
fun ScrollableUnitsScreen(
    navigateToUnit: (unitId: Int) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    listingsUiState: ListingsUiState,
    showContact: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(listingsUiState.unitsToDisplay.size) { index ->
            UnitItem(
                navigateToUnit = navigateToUnit,
                isRegistered = isRegistered,
                unit = listingsUiState.unitsToDisplay[index],
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
    navigateToUnit: (unitId: Int) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    unit: PropertyUnit,
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
                .clickable { navigateToUnit(unit.id) }
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
                        .clickable { navigateToUnit(unit.id) }
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
    newNotification: Boolean,
    numberOfNotifications: Int,
    navigationContentList: List<NavigationContent>,
    currentTab: BottomTab,
    onTabClicked: (currentTab: BottomTab) -> kotlin.Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
    ) {
        for ((index, navItem) in navigationContentList.withIndex()) {
            NavigationBarItem(
                selected = navItem.currentTab == currentTab,
                onClick = { onTabClicked(navItem.currentTab) },
                label = {
                        Text(text = navItem.title)
                },
                icon = {
                    if(newNotification) {
                        if(index == navigationContentList.lastIndex) {
                            Box(
                                modifier = Modifier
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Top,

                                ) {
                                    Icon(
                                        painter = navItem.icon,
                                        contentDescription = navItem.title,
//                                        Modifier.alpha(0.7f)
                                    )
                                    Text(
                                        text = numberOfNotifications.toString(),
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        modifier = Modifier
//                                            .align(Alignment.TopEnd)
                                    )
                                }

                            }
                        } else {
                            Icon(
                                painter = navItem.icon,
                                contentDescription = navItem.title
                            )
                        }
                    }
                }
            )
        }
//        for(navItem in navigationContentList) {
//            NavigationBarItem(
//                selected = navItem.currentTab == currentTab,
//                onClick = { onTabClicked(navItem.currentTab) },
//                label = { Text(text = navItem.title) },
//                icon = {
//                    if(newNotification) {
//                        Box {
//                            Icon(
//                                painter = navItem.icon,
//                                contentDescription = null
//                            )
//                            Text(text = numberOfNotifications.toString())
//                        }
//                    }
//
//                },
//
//            )
//        }
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
fun BottomNavigationBarPreview() {
    val navigationContentList = listOf<NavigationContent>(
        NavigationContent(
            title = "Listings",
            icon = painterResource(id = R.drawable.units_listing),
            currentTab = BottomTab.UNITS
        ),
        NavigationContent(
            title = "My Units",
            icon = painterResource(id = R.drawable.unit),
            currentTab = BottomTab.MY_UNITS,

            ),
        NavigationContent(
            title = "Notifications",
            icon = painterResource(id = R.drawable.notifications),
            currentTab = BottomTab.NOTIFICATIONS,

            ),
        NavigationContent(
            title = "Profile",
            icon = painterResource(id = R.drawable.account_tab),
            currentTab = BottomTab.ACCOUNT
        ),

        )
    PropertyManagementTheme {
        BottomNavigationBar(
            newNotification = true,
            numberOfNotifications = 3,
            navigationContentList = navigationContentList,
            currentTab = BottomTab.UNITS,
            onTabClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    PropertyManagementTheme {
        SearchField(
            searchLocation = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopMostBarPreview() {
    PropertyManagementTheme {
        TopMostBar(
            searchLocation = "",
            onSearchIconClicked = {},
            onStopSearchClicked = {},
            showSearchField = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarTextAndImagePreview() {
    PropertyManagementTheme {
        TopBarTextAndImage(
            onSearchIconClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBoxesPreview() {
    PropertyManagementTheme {
        FilterBoxes()
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableUnitsScreenCompactPreview(
    modifier: Modifier = Modifier
) {
    PropertyManagementTheme {
        PropertyScreen(
            onBackButtonPressed = {},
            navigateToRegistrationPage = {}
        )
    }
}


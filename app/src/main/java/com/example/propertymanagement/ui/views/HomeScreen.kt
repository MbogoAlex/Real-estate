package com.example.propertymanagement.ui.views

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.propertymanagement.R
import com.example.propertymanagement.apiServices.model.Category
import com.example.propertymanagement.apiServices.model.PropertyDataProperty
import com.example.propertymanagement.model.BottomTab
import com.example.propertymanagement.model.NavigationContent
import com.example.propertymanagement.model.UnitType
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import com.example.propertymanagement.ui.views.advertisenment.UserAdvertsScreen
import kotlinx.coroutines.launch

object HomeDestination: NavigationDestination {
    override val route: String = "Home"
    override val titleRes: Int = R.string.units_screen
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertyScreen(
    navigateToUnit: (unitId: String) -> kotlin.Unit = {id -> },
    navigateToAdvertDetails: (unitId: String) -> kotlin.Unit = {id -> },
    onBackButtonPressed: () -> Unit,
    navigateToRegistrationPage: () -> kotlin.Unit,
    navigateToLoginScreen: (phoneNumber: String, password: String) -> Unit,
    navigateToCreatePropertyScreen: () -> Unit,
    proceedToLogin: (phoneNumber: String, password: String) -> kotlin.Unit,
    onLoadHomeScreen: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.forceLogin) {
        Toast.makeText(context, "You are not logged in", Toast.LENGTH_SHORT).show()
        Log.i("LOGIN_WITH_CRED: ", "phone: ${uiState.userDetails.phoneNumber}, pass: ${uiState.userDetails.password}")
        proceedToLogin(
            uiState.userDetails.phoneNumber,
            uiState.userDetails.password
        )
        viewModel.undoForcedLogin()
    } else if(uiState.forceRegister) {
        Toast.makeText(context, "You are not registered yet", Toast.LENGTH_SHORT).show()
        navigateToRegistrationPage()
        viewModel.undoForcedRegistration()
    }

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
            title = "Advertise",
            icon = painterResource(id = R.drawable.upload_property),
            currentTab = BottomTab.UPLOAD_PROPERTY,

            ),
        NavigationContent(
            title = "Profile",
            icon = painterResource(id = R.drawable.account_tab),
            currentTab = BottomTab.ACCOUNT
        ),

        )

    Column {
        when(uiState.bottomTab) {
            BottomTab.UNITS -> {
                UnitsScreen(
                    uiState = uiState,
                    onTabClicked = { currentTab ->  },
                    filterListing = { categoryId -> viewModel.fetchListings(
                        categoryId,
                        uiState.userDetails.token
                        ) },
                    showContact = {
                        showRegisterUserAlert = !uiState.isLoggedIn
                    },
                    navigateToUnit = {
                        navigateToUnit(it.toString())
                    },
                    isRegistered = uiState.isLoggedIn,
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
            BottomTab.UPLOAD_PROPERTY -> {
                UserAdvertsScreen(
                    navigateToCreatePropertyScreen = navigateToCreatePropertyScreen,
                    navigateToAdvertDetails = {
                        navigateToAdvertDetails(it)
                    },
                    modifier = Modifier
                        .weight(1f)
                )
            }

            BottomTab.ACCOUNT -> {
                AccountScreen(
                    onLoadHomeScreen = onLoadHomeScreen,
                    loadRegistrationScreen = navigateToRegistrationPage,
                    loadLoginScreen = { phoneNumber, password ->
                                  navigateToLoginScreen(phoneNumber, password)
                    },
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
        BottomNavigationBar(
            navigationContentList = navigationContentList,
            currentTab = uiState.bottomTab,
            newNotification = true,
            numberOfNotifications = 3,
            onTabClicked = { currentTab ->  viewModel.switchTab(currentTab)},
        )
    }



}

@Composable
fun UnitsScreen(
    uiState: HomeScreenUiState,
    navigateToUnit: (unitId: Int) -> Unit,
//    onBackButtonPressed: () -> kotlin.Unit,
//    showSelectedUnit: (unitId: Int) -> kotlin.Unit,
    onTabClicked: (currentTab: BottomTab) -> kotlin.Unit,
    isRegistered: Boolean,
    showContact: () -> kotlin.Unit,
    filterListing: (categoryId: String) -> Unit,
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
            categories = uiState.listingsData.categories,
            searchLocation = "",
            onSearchIconClicked = {
                showSearchField = true
            },
            onStopSearchClicked = {
                showSearchField = false
            },
            showSearchField = showSearchField,
            filterListing = filterListing
        )
        ScrollableUnitsScreen(
            navigateToUnit = navigateToUnit,
//            showSelectedUnit = showSelectedUnit,
            isRegistered = isRegistered,
            uiState = uiState,
            showContact = showContact,
            modifier = Modifier
                .weight(1f)

//                    .padding(10.dp)
        )
    }
}

@Composable
fun TopMostBar(
    categories: List<Category>,
    searchLocation: String,
    onSearchIconClicked: () -> kotlin.Unit,
    onStopSearchClicked: () -> kotlin.Unit,
    filterListing: (categoryId: String) -> Unit,
    showSearchField: Boolean,
    modifier: Modifier = Modifier
) {
    var filteredLocations by rememberSaveable {
        mutableStateOf(countiesListManipulation(""))
    }

    var locationQuery by rememberSaveable {
        mutableStateOf("")
    }
    var showFilterLocationsBox by rememberSaveable {
        mutableStateOf(false)
    }

    var showDropDown by rememberSaveable {
        mutableStateOf(true)
    }
    var selectedLocation by rememberSaveable {
        mutableStateOf("Nairobi")
    }
    var location by rememberSaveable {
        mutableStateOf("")
    }
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
        TopBarTextAndImage(
            selectedLocation = selectedLocation,
            onShowLocationsBox = {
                showFilterLocationsBox = !showFilterLocationsBox
            },

            onSearchIconClicked = { onSearchIconClicked() }
        )
        FilterBoxes(
            categories = categories,
            filterListing = filterListing
        )
    }

}

@Composable
fun TopBarTextAndImage(
    selectedLocation: String,
    onShowLocationsBox: () -> Unit,
    onSearchIconClicked: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        elevation = 9.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
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
//            FilterLocationDropDownMenu(
//                selectedLocation = selectedLocation,
//                modifier = Modifier
//                    .clickable {
//                        onShowLocationsBox()
//
//                    }
//            )
//            Spacer(modifier = Modifier.weight(1f))
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

//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = null,
//                modifier = Modifier
//                    .clickable {
//                        onSearchIconClicked()
//                    }
//            )

        }
    }
}

@Composable
fun SearchField(
    onValueChange: (value: String) -> Unit,
    value: String,
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
            value = value,
            placeholder = {
                          Text(
                              text = "Enter county name",
                              fontWeight = FontWeight.Light
                          )
            },
            onValueChange = onValueChange,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
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
    categories: List<Category>,
    filterListing: (categoryId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBoxValue by rememberSaveable {
        mutableStateOf(1)
    }
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
            categories.forEach {
                BoxMenuItem(
                    selectedBoxValue = selectedBoxValue,
                    boxValueName = it.name,
                    boxValueId = it.id.toString(),
                    modifier = Modifier
                        .clickable {
                            selectedBoxValue = it.id
                            filterListing(it.id.toString())
                        }
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }

    }
}

@Composable
fun BoxMenuItem(
    selectedBoxValue: Int,
    boxValueName: String,
    boxValueId: String,
    modifier: Modifier = Modifier
) {
    var color = Color.Transparent
    if(boxValueId.toInt() == selectedBoxValue) {
        color = Color.Cyan
    }

    Card(

        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        modifier = modifier
            .padding(
                start = 5.dp,
            )

    ) {
        Text(
            text = boxValueName,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .widthIn(min = 90.dp)
                .padding(10.dp)
        )
    }
}

@Composable
fun ScrollableUnitsScreen(
    navigateToUnit: (unitId: Int) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    uiState: HomeScreenUiState,
    showContact: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    val listings = uiState.listingsData.listings.reversed()

    if(uiState.listingsLoadingState == ListingsLoadingState.LOADING) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(10) {
                UnitItemPlaceholder()
            }

        }
    } else if(uiState.listingsLoadingState == ListingsLoadingState.SUCCESS) {
        if(listings.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = "No items for this category")
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(listings) {
                    UnitItem(
                        navigateToUnit = navigateToUnit,
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

    }

}

@Composable
fun UnitItem(
    navigateToUnit: (unitId: Int) -> kotlin.Unit = {id -> },
    isRegistered: Boolean,
    unit: PropertyDataProperty,
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
                .clickable { navigateToUnit(unit.propertyId) }
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
                        .clickable { navigateToUnit(unit.propertyId) }
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

@Composable
fun UnitItemPlaceholder(
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

    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray
            ),
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) {

        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Loading...",
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
                    text = "Loading...",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)

            )
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
                    Icon(
                        painter = navItem.icon,
                        contentDescription = navItem.title
                    )
                }
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
fun FilterLocationDropDownMenu(
    selectedLocation: String,
    modifier: Modifier = Modifier
) {
    var showDropDown by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()

    var filteredCounties by rememberSaveable {
        mutableStateOf(countiesListManipulation(""))
    }

    var query by rememberSaveable {
        mutableStateOf("")
    }

    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .widthIn(150.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedLocation,
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 10.dp,
                    end = 10.dp,

                )
            )
            if(showDropDown) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

        }

    }



}

@Composable
fun LocationFilterBox(
    showDropDown: Boolean,
    onHideDropDown: () -> Unit,
    onSelectItem: (index: Int) -> Unit,
    onValueChange: (value: String) -> Unit,
    value: String,
    filteredCounties: List<String>,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
//                            .width(150.dp)
            .padding(top = 50.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .heightIn(max = 300.dp)
        ) {
            LocationEntryTextField(
                onValueChange = onValueChange,
                value = value
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                filteredCounties.forEachIndexed { index, county ->
                    Text(
                        text = county,
                        modifier = Modifier
                            .padding(
                                start = 5.dp,
                                top = 15.dp,
                                bottom = 5.dp
                            )
                            .clickable {
                                onSelectItem(index)
                            }
                            .fillMaxWidth()
                            .align(
                                Alignment.CenterHorizontally
                            )
                    )
                    Divider(
                        modifier = Modifier
//                                            .width(150.dp)
                    )
                }
            }
        }


    }
}

@Composable
fun LocationEntryTextField(
    onValueChange: (value: String) -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            label = {
                    Text(text = "Location")
            },
            value = value,
            onValueChange = onValueChange,
//            colors = TextFieldDefaults.textFieldColors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            )
        )
    }

}

private fun countiesListManipulation(query: String): List<String> {
    val counties = mutableListOf<String>(
        "Mombasa",
        "Kwale",
        "Kilifi",
        "Tana River",
        "Lamu",
        "Taita Taveta",
        "Garissa",
        "Wajir",
        "Mandera",
        "Marsabit",
        "Isiolo",
        "Meru",
        "Tharaka Nithi",
        "Embu",
        "Kitui",
        "Machakos",
        "Makueni",
        "Nyandarua",
        "Nyeri",
        "Kirinyaga",
        "Murang'a",
        "Nairobi",
        "Nakuru",
        "Narok",
        "Kajiado",
        "Kericho",
        "Bomet",
        "Kakamega",
        "Vihiga",
        "Bungoma",
        "Busia",
        "Siaya",
        "Kisumu",
        "Homa Bay",
        "Migori",
        "Kisii",
        "Nyamira",
        "Turkana",
        "West Pokot",
        "Samburu",
        "Trans Nzoia",
        "Uasin Gishu",
        "Elgeyo-Marakwet",
        "Nandi",
        "Baringo",
        "Laikipia",
        "Nakuru",
        "Narok",
        "Kajiado",
        "Kericho",
        "Bomet",
        "Kakamega",
        "Vihiga",
        "Bungoma",
        "Busia",
        "Siaya",
        "Kisumu",
        "Homa Bay",
        "Migori",
        "Kisii",
        "Nyamira",
        "Turkana",
        "West Pokot",
        "Samburu",
        "Trans Nzoia",
        "Uasin Gishu",
        "Elgeyo-Marakwet",
        "Nandi",
        "Baringo",
        "Laikipia",
        "Nakuru",
        "Narok",
        "Kajiado",
        "Kericho",
        "Bomet",
        "Kakamega",
        "Vihiga",
        "Bungoma",
        "Busia",
        "Siaya",
        "Kisumu",
        "Homa Bay",
        "Migori",
        "Kisii",
        "Nyamira",
        "Turkana",
        "West Pokot",
        "Samburu",
        "Trans Nzoia",
        "Uasin Gishu",
        "Elgeyo-Marakwet",
        "Nandi",
        "Baringo",
        "Laikipia",
        "Nakuru",
        "Narok",
        "Kajiado",
        "Kericho",
        "Bomet",
        "Kakamega",
        "Vihiga",
        "Bungoma",
        "Busia",
        "Siaya",
        "Kisumu",
        "Homa Bay",
        "Migori",
        "Kisii",
        "Nyamira",
        "Turkana",
        "West Pokot",
        "Samburu",
        "Trans Nzoia",
        "Uasin Gishu",
        "Elgeyo-Marakwet",
        "Nandi",
        "Baringo",
        "Laikipia",
        "Nakuru",
        "Narok",
        "Kajiado",
        "Kericho",
        "Bomet",
        "Kakamega",
        "Vihiga",
        "Bungoma",
        "Busia",
        "Siaya",
        "Kisumu",
        "Homa Bay",
        "Migori",
        "Kisii",
        "Nyamira",
        "Turkana",
        "West Pokot",
        "Samburu",
        "Trans Nzoia",
        "Uasin Gishu",
        "Elgeyo-Marakwet",
        "Nandi",
        "Baringo",
        "Laikipia",
        "Nakuru",
        "Narok",
        "Kajiado",
        "Kericho",
        "Bomet",
        "Kakamega",
        "Vihiga",
        "Bungoma",
        "Busia",
        "Siaya",
        "Kisumu",
        "Homa Bay",
        "Migori",
        "Kisii",
        "Nyamira",
        "Turkana",
        "West Pokot",
        "Samburu",
    )
    if(query.isEmpty()) {
        return emptyList()
    } else {
        return counties.filter { it.contains(query, ignoreCase = true) }.distinct()
    }
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
            title = "Advertise",
            icon = painterResource(id = R.drawable.upload_property),
            currentTab = BottomTab.UPLOAD_PROPERTY,

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
fun FilterLocationDropDownMenuPreview() {
    PropertyManagementTheme {
        FilterLocationDropDownMenu(
            selectedLocation = "Nairobi",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationEntryTextFieldPreview() {
    PropertyManagementTheme {
        LocationEntryTextField(
            value = "",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    PropertyManagementTheme {
        SearchField(
            searchLocation = "",
            value = "",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopMostBarPreview() {
    PropertyManagementTheme {
        TopMostBar(
            categories = emptyList(),
            searchLocation = "",
            onSearchIconClicked = {},
            onStopSearchClicked = {},
            filterListing = {},
            showSearchField = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarTextAndImagePreview() {
    PropertyManagementTheme {
        TopBarTextAndImage(
            selectedLocation = "Nairobi",
            onShowLocationsBox = {},
            onSearchIconClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBoxesPreview() {
    PropertyManagementTheme {
        FilterBoxes(
            filterListing = {},
            categories = emptyList()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ScrollableUnitsScreenCompactPreview(
    modifier: Modifier = Modifier
) {
    PropertyManagementTheme {
        PropertyScreen(
            onBackButtonPressed = {},
            navigateToRegistrationPage = {},
            proceedToLogin = {phoneNumber, password ->  },
            navigateToLoginScreen = {phoneNumber, password ->  },
            navigateToCreatePropertyScreen = {},
            onLoadHomeScreen = {}
        )
    }
}


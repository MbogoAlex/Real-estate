package com.example.propertymanagement.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertymanagement.R
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

@Composable
fun AccountScreen(
    onLoadHomeScreen: () -> Unit,
    loadRegistrationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: AccountScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.accountScreenUiState.collectAsState()

    val homeScreenViewModel: HomeScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelFactory.Factory)
    val homeUiState by homeScreenViewModel.listingsUiState.collectAsState()
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    var showLoginAlert by remember {
        mutableStateOf(false)
    }
    if(showDeleteDialog) {
        AlertDialogTemplate(
            onDismissRequest = { showDeleteDialog = false },
            onConfirmRequest = {
                viewModel.userLogout()
                Toast.makeText(context, "You are logged out", Toast.LENGTH_SHORT).show()
                showDeleteDialog = false
                onLoadHomeScreen()
            },
            confirmButtonText = "Log out",
            titleText = "Log out?",
            bodyText = "Confirm logging out"
        )
    }
    if(showLoginAlert) {
        AlertDialogTemplate(
            onDismissRequest = { showLoginAlert = false },
            onConfirmRequest = {
                               loadRegistrationScreen()
                showLoginAlert = false
            },
            confirmButtonText = "Confirm",
            titleText = "Register / Log in",
            bodyText = "Confirm registration or log in if you already have an account"
        )
    }
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .padding(
//                start = 10.dp,
//                end = 10.dp
                )
                .fillMaxSize()
        ) {
            AccountTopBar()
            Spacer(modifier = Modifier.height(20.dp))
            ProfileSection(
                userName = uiState.name.takeIf { it.isEmpty() } ?: "Username"
            )
            Spacer(modifier = Modifier.height(20.dp))
            AccountOptions(
                onNotificationsCardClicked = { /*TODO*/ },
                onEditProfileCardClicked = { /*TODO*/ },
                onChangePasswordCardClicked = { /*TODO*/ },
                onTermsAndConditionsCardClicked = { /*TODO*/ },
                onPrivacyPolicyCardClicked = { /*TODO*/ },
                onDeleteAccountCardClicked = {  },
                onLogoutCardClicked = { showDeleteDialog = true },
                onLogInCardClicked = { showLoginAlert = true },
                isRegistered = uiState.userId != null
            )
        }
    }


}

@Composable
fun ProfileSection(
    userName: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_placeholder),
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Text(
            text = userName,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AccountOptions(
    onNotificationsCardClicked: () -> Unit,
    onEditProfileCardClicked: () -> Unit,
    onChangePasswordCardClicked: () -> Unit,
    onTermsAndConditionsCardClicked: () -> Unit,
    onPrivacyPolicyCardClicked: () -> Unit,
    onDeleteAccountCardClicked: () -> Unit,
    onLogoutCardClicked: () -> Unit,
    onLogInCardClicked: () -> Unit,
    isRegistered: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        ),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(ScrollState(0))
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            if(isRegistered) {
                ProfileMenuItem(
                    painter = painterResource(id = R.drawable.notifications),
                    onMenuCardClicked = onNotificationsCardClicked,
                    menuTitle = "Notifications"
                )
                ProfileMenuItem(
                    painter = painterResource(id = R.drawable.edit),
                    onMenuCardClicked = onEditProfileCardClicked,
                    menuTitle = "Edit Profile"
                )
                ProfileMenuItem(
                    painter = painterResource(id = R.drawable.lock),
                    onMenuCardClicked = onChangePasswordCardClicked,
                    menuTitle = "Change Password"
                )
            }
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.terms_and_conditions),
                onMenuCardClicked = onTermsAndConditionsCardClicked,
                menuTitle = "Terms & Conditions"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.privacy_policy),
                onMenuCardClicked = onPrivacyPolicyCardClicked,
                menuTitle = "Privacy Policy"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.delete),
                onMenuCardClicked = onDeleteAccountCardClicked,
                menuTitle = "Delete Account"
            )
            if(isRegistered) {
                ProfileMenuItem(
                    painter = painterResource(id = R.drawable.logout),
                    onMenuCardClicked = onLogoutCardClicked,
                    menuTitle = "Logout"
                )
            } else {
                ProfileMenuItem(
                    painter = painterResource(id = R.drawable.logout),
                    onMenuCardClicked = onLogInCardClicked,
                    menuTitle = "Register / Log in"
                )
            }
        }
    }
}

@Composable
fun AlertDialogTemplate(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    confirmButtonText: String,
    titleText: String,
    bodyText: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            androidx.compose.material.TextButton(onClick = { onConfirmRequest() }) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            androidx.compose.material.TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Dismiss")
            }
        },
        title = {
            Text(text = titleText)
        },
        text = {
            Text(text = bodyText)
        },
        modifier = modifier
    )
}

@Composable
fun ProfileMenuItem(
    painter: Painter,
    menuTitle: String,
    onMenuCardClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 16.dp
            )
            .clickable {
                onMenuCardClicked()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painter,
                    contentDescription = menuTitle
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = menuTitle)
            }
        }
    }
}

@Composable
fun AccountTopBar(
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
                text = "Account",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LogOutAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
    modifier: Modifier
) {
    AlertDialog(
        title = {
                Text(text = "Log out?")
        },
        text = {
               Text(text = "Are you sure you want to log out")
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
                        TextButton(onClick = { onConfirmButton() }) {
                            Text(text = "Confirm")
                        }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Dismiss")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileMenuItemPreview() {
    PropertyManagementTheme {
        ProfileMenuItem(
            painter = painterResource(id = R.drawable.edit),
            onMenuCardClicked = {},
            menuTitle = "Edit Profile"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PropertyManagementTheme {
        AccountScreen(
            onLoadHomeScreen = {},
            loadRegistrationScreen = {}
        )
    }
}
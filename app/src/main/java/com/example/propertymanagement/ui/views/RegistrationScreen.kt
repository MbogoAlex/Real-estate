package com.example.propertymanagement.ui.views

import android.util.Log
import android.widget.Space
import android.widget.Toast
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propertymanagement.R
import com.example.propertymanagement.ui.AppViewModelFactory
import com.example.propertymanagement.ui.nav.NavigationDestination
import com.example.propertymanagement.ui.theme.PropertyManagementTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object RegistrationScreenDestination: NavigationDestination {
    override val route: String = "Registration"
    override val titleRes: Int = R.string.registration_screen
}
@Composable
fun RegistrationScreen(
    onBackButtonPressed: () -> kotlin.Unit,
    proceedToLogin: (phoneNumber: String, password: String) -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    var num = 1
    val viewModel: RegistrationViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var loadingIndicator by remember {
        mutableStateOf(false)
    }

    when(uiState.registrationStatus) {
        RegistrationStatus.START -> {}
        RegistrationStatus.LOADING -> loadingIndicator = true
        RegistrationStatus.SUCCESS -> {
            num += 1
            Log.e("REGISTER_LOOP", "THIS CODE IS RUNNING INFINITELY: $num")
            loadingIndicator = false
            proceedToLogin(
                uiState.userDetails.phoneNumber,
                uiState.userDetails.password
            )
            viewModel.updateRegistrationStatusToDone()
        }
        RegistrationStatus.FAIL -> {
            Toast.makeText(context, "Authentication failed. Try again later", Toast.LENGTH_SHORT).show()
            loadingIndicator = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (loadingIndicator) {
            false -> {
                Column(
//        verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onBackButtonPressed()
                            }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Register",
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)

                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    label = {
                                        Text(text = "First name")
                                    },
                                    value = uiState.userDetails.firstName,
                                    onValueChange = {
                                        viewModel.userDetails = viewModel.userDetails.copy(
                                            firstName = it
                                        )
                                        viewModel.updateRegistrationScreenUi()
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),

                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                OutlinedTextField(
                                    label = {
                                        Text(text = "Last name")
                                    },
                                    value = uiState.userDetails.lastName,
                                    onValueChange = {
                                        viewModel.userDetails = viewModel.userDetails.copy(
                                            lastName = it
                                        )
                                        viewModel.updateRegistrationScreenUi()
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                label = {
                                    Text(text = "Email")
                                },
                                value = uiState.userDetails.email,
                                onValueChange = {
                                    viewModel.userDetails = viewModel.userDetails.copy(
                                        email = it
                                    )
                                    viewModel.updateRegistrationScreenUi()
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Done
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                label = {
                                    Text(text = "Phone Number")
                                },
                                value = uiState.userDetails.phoneNumber,
                                onValueChange = {
                                    viewModel.userDetails = viewModel.userDetails.copy(
                                        phoneNumber = it
                                    )
                                    viewModel.updateRegistrationScreenUi()
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Phone,
                                    imeAction = ImeAction.Done
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                label = {
                                    Text(text = "Password")
                                },
                                value = uiState.userDetails.password,
                                onValueChange = {
                                    viewModel.userDetails = viewModel.userDetails.copy(
                                        password = it
                                    )
                                    viewModel.updateRegistrationScreenUi()
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                                visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val eyeIcon = if(passwordVisible) R.drawable.visibity_on else
                                        R.drawable.visibility_off
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(painter = painterResource(id = eyeIcon), contentDescription = null)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = "A 5-digit pin will be sent to your phone number"
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Button(
                                onClick = {
                                    viewModel.registerUser()

                                    Log.i("REG_SUCCESS", "${uiState.registrationSuccess}")


                                },
                                enabled = uiState.buttonEnabled,
                                modifier = Modifier
                                    .widthIn(250.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Row {
                                    Text(
                                        text = "Continue"
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "Continue with registration"
                                    )
                                }
                            }

                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Already registered?",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "Sign in",
                            style = TextStyle(color = Color.Red),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    proceedToLogin("0", "0")
                                }
                        )
                    }
                }
            }
            true -> {
                CircularIndicator(
                    modifier = Modifier
                        .size(48.dp)
                )
            }
        }
    }

}

@Composable
fun CircularIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        progress = 0.5f,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun RegistrationCompactScreen()
{
    PropertyManagementTheme {
        RegistrationScreen(
            onBackButtonPressed = {},
            proceedToLogin = { phoneNumber, password ->  }
        )
    }
}
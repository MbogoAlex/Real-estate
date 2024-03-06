package com.example.propertymanagement.ui.views

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: RegistrationViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember {
        mutableStateOf(false)
    }
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
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RegistrationCompactScreen()
{
    PropertyManagementTheme {
        RegistrationScreen()
    }
}
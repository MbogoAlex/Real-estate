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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier
) {
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
                OutlinedTextField(
                    value = "Name",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    value = "Email",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = "Phone Number",
                    onValueChange = {},
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
                    onClick = { /*TODO*/ },
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
package com.example.propertymanagement.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

@Composable
fun InvoiceScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        InvoiceHeader()
        Column() {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                InvoiceCardDetails(
                    modifier = Modifier
                        .padding(20.dp)
                )
            }


        }
        Spacer(modifier = Modifier.height(150.dp))
        PaymentButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun InvoiceCardDetails(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "{Property name} Monthly Invoice",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold

        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Invoice generated on: 23/03/2024",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "House rent:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ksh.15,000",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Water bill:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ksh.600",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Dustbin collection:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ksh.200",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider(thickness = 3.dp)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Total:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ksh.15,800",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InvoiceHeader(
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
            text = "Invoice",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,

            )
    }
}

@Composable
fun PaymentButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
            .widthIn(250.dp)

    ) {
        Text(text = "Pay Invoice")
    }
}

@Preview(showBackground = true)
@Composable fun InvoiceScreenPreview() {
    PropertyManagementTheme {
        InvoiceScreen()
    }
}
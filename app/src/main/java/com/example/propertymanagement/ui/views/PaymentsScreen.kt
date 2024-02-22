package com.example.propertymanagement.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

@Composable
fun PaymentsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        PaymentsScreenHeader()
        LazyColumn(

        ) {
            items(5) {
                PaymentItem(
                    modifier = Modifier
                        .padding(20.dp)
                )
            }
        }
    }
}

@Composable
fun PaymentItem(
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Rent payment",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Rent for February"
                )
            }
            Column {
                Text(
                    text = "Ksh.17,200",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Green),
                    modifier = Modifier
                        .align(Alignment.End)
                )
                Text(
                    text = "Paid on: 27/2/2023"
                )
            }
        }
        Divider()
    }
}

@Composable
fun PaymentsScreenHeader(
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
            text = "Payments",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,

            )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentsScreenPreview() {
    PropertyManagementTheme {
        PaymentsScreen()
    }
}
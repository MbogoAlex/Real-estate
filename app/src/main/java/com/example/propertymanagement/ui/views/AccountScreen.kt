package com.example.propertymanagement.ui.views

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propertymanagement.R
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Account Details",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        ProfileSection()
        Spacer(modifier = Modifier.height(20.dp))
        AccountOptions()
    }

}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mary Jane",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(3.dp))
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .height(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "Edit Profile",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    color = Color.Blue
                ),
                modifier = Modifier
                    .clickable {  }
            )
        }
        Text(
            text = "Log Out",
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                color = Color.Red
            ),
            modifier = Modifier
                .padding(
                    top = 5.dp
                )
                .clickable { }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            thickness = 2.dp,
            color = Color.Black
        )



    }
}

@Composable
fun AccountOptions(
    modifier: Modifier = Modifier
) {
    Column {
        InvoicesCard()
        Spacer(modifier = Modifier.height(20.dp))
        PaymentsCard()
    }
}

@Composable
fun InvoicesCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Box {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.invoice_tab),
                        contentDescription = "Check your invoices",
                        modifier = Modifier
                            .size(80.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = "Invoice",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Check all your invoices"
                        )
                    }

                }

            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Check",
                modifier = Modifier

            )
        }

    }
}

@Composable
fun PaymentsCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Box {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.payments_tab),
                        contentDescription = "Check your payments",
                        modifier = Modifier
                            .size(80.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = "Payments",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Check all your payments"
                        )
                    }

                }

            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Check",
                modifier = Modifier

            )
        }

    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AccountPageAppBar(
//    modifier: Modifier = Modifier
//) {
//    TopAppBar(title = {
//        Text(
//            text = "Account",
//            fontWeight = FontWeight.Bold
//        )
//    })
//}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PropertyManagementTheme {
        AccountScreen()
    }
}
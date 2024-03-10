package com.example.propertymanagement.ui.views

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
            ProfileSection()
            Spacer(modifier = Modifier.height(20.dp))
            AccountOptions()
        }
    }

}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
        Text(
            text = "Mary Jane",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AccountOptions(
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
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.notifications),
                menuTitle = "Notifications"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.edit),
                menuTitle = "Edit Profile"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.lock),
                menuTitle = "Change Password"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.terms_and_conditions),
                menuTitle = "Terms & Conditions"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.privacy_policy),
                menuTitle = "Privacy Policy"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.delete),
                menuTitle = "Delete Account"
            )
            ProfileMenuItem(
                painter = painterResource(id = R.drawable.logout),
                menuTitle = "Logout"
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    painter: Painter,
    menuTitle: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 16.dp
            )
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

@Preview(showBackground = true)
@Composable
fun ProfileMenuItemPreview() {
    PropertyManagementTheme {
        ProfileMenuItem(
            painter = painterResource(id = R.drawable.edit),
            menuTitle = "Edit Profile"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PropertyManagementTheme {
        AccountScreen()
    }
}
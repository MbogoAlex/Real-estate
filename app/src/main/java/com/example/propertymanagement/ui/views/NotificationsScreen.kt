package com.example.propertymanagement.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propertymanagement.ui.theme.PropertyManagementTheme

@Composable
fun NotificationsScreen(
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
            text = "Notifications",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(25) {
                NotificationItem()
            }
        }
    }

}

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier
) {
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification icon",
                modifier = Modifier
                    .size(20.dp)
            )
            Column {
                Text(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "1 March 19:04",
                    fontWeight = FontWeight.Light
                )
            }
        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    PropertyManagementTheme {
        NotificationsScreen()
    }
}
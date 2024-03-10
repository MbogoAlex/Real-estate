package com.example.propertymanagement.utils

import java.text.SimpleDateFormat
import java.util.Date

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}
package com.example.propertymanagement.utils

import com.example.propertymanagement.SFServices.model.SFUserDetails


fun SFUserDetails.toLoggedInUserDetails(): LoggedInUserDetails = LoggedInUserDetails(
    userId = userId,
    name = "$userFirstName $userLastName",
    email  = userEmail,
    phoneNumber = userPhoneNumber,
    token = token,
)
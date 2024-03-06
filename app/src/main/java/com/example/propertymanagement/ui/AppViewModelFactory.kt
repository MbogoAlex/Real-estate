package com.example.propertymanagement.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.propertymanagement.PManagerApplication
import com.example.propertymanagement.ui.views.RegistrationViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {
        //initialize PManager API Repository
        initializer {
            val pMangerApiRepository = pManagerApplication().container.pMangerApiRepository
            RegistrationViewModel(pMangerApiRepository = pMangerApiRepository)
        }
    }
}

fun CreationExtras.pManagerApplication(): PManagerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PManagerApplication)
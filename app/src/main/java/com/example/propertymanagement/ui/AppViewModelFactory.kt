package com.example.propertymanagement.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.propertymanagement.PManagerApplication
import com.example.propertymanagement.ui.appViewModel.AppViewModel
import com.example.propertymanagement.ui.views.AccountScreenViewModel
import com.example.propertymanagement.ui.views.HomeScreenViewModel
import com.example.propertymanagement.ui.views.LoginViewModel
import com.example.propertymanagement.ui.views.RegistrationViewModel
import com.example.propertymanagement.ui.views.UnitsDetailsScreenViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {
        //initialize PManager API Repository
        initializer {
            val pManagerApiRepository = pManagerApplication().container.pMangerApiRepository
            val pManagerSFRepository =  pManagerApplication().pManagerSFRepository

            AppViewModel(
                pManagerApiRepository = pManagerApiRepository,
                pManagerSFRepository = pManagerSFRepository
            )
        }

        //initialize PManager Shared Prefs for LoginViewModel
        initializer {
            val pManagerApiRepository = pManagerApplication().container.pMangerApiRepository
            val pManagerSFRepository =  pManagerApplication().pManagerSFRepository
            LoginViewModel(
                pMangerApiRepository = pManagerApiRepository,
                pManagerSFRepository = pManagerSFRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        //initialize HomeScreenViewModel
        initializer {
            val pManagerApiRepository = pManagerApplication().container.pMangerApiRepository
            val pManagerSFRepository =  pManagerApplication().pManagerSFRepository
            HomeScreenViewModel(
                pManagerApiRepository = pManagerApiRepository,
                pManagerSFRepository = pManagerSFRepository
            )
        }

        //initialize UnitDetailsViewModel
        initializer {
            val pManagerApiRepository = pManagerApplication().container.pMangerApiRepository
            val pManagerSFRepository =  pManagerApplication().pManagerSFRepository
            UnitsDetailsScreenViewModel(
                pManagerApiRepository = pManagerApiRepository,
                pManagerSFRepository = pManagerSFRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        initializer {
            val pManagerApiRepository = pManagerApplication().container.pMangerApiRepository
            RegistrationViewModel(pMangerApiRepository = pManagerApiRepository)
        }

        //initialize AccountScreenViewModel
        initializer {
            val pManagerSFRepository = pManagerApplication().pManagerSFRepository
            AccountScreenViewModel(
                pManagerSFRepository = pManagerSFRepository
            )
        }


    }
}

fun CreationExtras.pManagerApplication(): PManagerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PManagerApplication)
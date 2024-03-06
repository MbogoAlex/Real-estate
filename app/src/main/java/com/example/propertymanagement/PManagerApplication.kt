package com.example.propertymanagement

import android.app.Application
import com.example.propertymanagement.container.AppContainer
import com.example.propertymanagement.container.DefaultContainer

class PManagerApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
    }
}